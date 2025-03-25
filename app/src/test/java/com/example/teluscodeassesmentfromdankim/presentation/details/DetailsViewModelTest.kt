@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.teluscodeassesmentfromdankim.presentation.details

import androidx.lifecycle.SavedStateHandle
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.domain.repository.MovieListRepository
import com.example.teluscodeassesmentfromdankim.utils.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Author: Dan Kim
 *
 * Tests the DetailsViewModel which loads a single movie's detail using movieId
 * from SavedStateHandle and emits a loading state followed by a success or error.
 */
class DetailsViewModelTest {
    private lateinit var repository: MovieListRepository
    private lateinit var savedStateHandle: SavedStateHandle
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        // Set main dispatcher to test dispatcher for coroutine control
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        // Provide fake movieId to SavedStateHandle (used in ViewModel init)
        savedStateHandle = SavedStateHandle(mapOf(DetailsConstants.KEY_MOVIE_ID to 1))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init loads movie details successfully`() = runTest {
        // Arrange: Create a fake movie to be returned from repository
        val movie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "Overview",
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            originalTitle = "Original Title"
        )
        // Mock repository to emit loading -> success -> loading(false)
        coEvery { repository.getMovie(1) } returns flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(movie))
            emit(Resource.Loading(false))
        }

        // Act: Create ViewModel (triggers init block which calls getMovie)
        val viewModel = DetailsViewModel(repository, savedStateHandle)

        // Let coroutines complete
        advanceUntilIdle()

        // Assert: ViewModel state should have the expected movie
        val state = viewModel.state
        assertEquals(false, state.isLoading)
        assertEquals("Test Movie", state.movie?.title)
    }

    @Test
    fun `init emits error when movie is null`() = runTest {
        // Arrange: Repository emits null to simulate an error scenario
        coEvery { repository.getMovie(1) } returns flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(null)) // Simulate failure
            emit(Resource.Loading(false))
        }
        // Act: Create ViewModel
        val viewModel = DetailsViewModel(repository, savedStateHandle)
        // Let coroutines finish
        advanceUntilIdle()
        // Assert: No movie should be available in state
        val state = viewModel.state
        assertFalse(state.isLoading)
        assertNull(state.movie)
    }

    @Test
    fun `init emits error state when repository fails`() = runTest {
        // Arrange: Simulate error
        coEvery { repository.getMovie(1) } returns flow {
            emit(Resource.Loading(true))
            emit(Resource.Error("Network failure"))
            emit(Resource.Loading(false))
        }

        // Act
        val viewModel = DetailsViewModel(repository, savedStateHandle)
        advanceUntilIdle()

        // Assert
        val state = viewModel.state
        assertFalse(state.isLoading)
        assertNull(state.movie) // Should still be null
    }

}