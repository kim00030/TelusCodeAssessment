@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.teluscodeassesmentfromdankim.presentation.home

import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.data.remote.repository.MovieListRepositoryImpl
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
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
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Author: Dan Kim
 *
 * Unit tests for HomeViewModel.
 * These tests verify:
 * 1. That the ViewModel loads the initial movie list during initialization (page 1)
 * 2. That it correctly paginates and appends more movies to the list when onEvent(Paginate) is triggered.
 * Note: SLF4J warnings may appear in logs but are safe to ignore.
 */
class HomeViewModelTest {

    private lateinit var movieListRepository: MovieListRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieListRepository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `init block loads movies on viewModel creation`() = runTest {
        // Arrange: prepare fake movie data for page 1
        val fakeMovie = Movie(
            id = 1,
            title = "Test Movie",
            overview = "A test movie",
            posterPath = "/test.jpg",
            backdropPath = "/backdrop.jpg",
            originalTitle = "Test Movie Original"
        )
        val fakeMovies = listOf(fakeMovie)

        // Mock repository behavior for page 1 (called from init block)
        coEvery {
            movieListRepository.getMovieList(
                needToFetchFromRemote = false,
                personId = ApiConstants.PEOPLE_ID,
                page = 1
            )
        } returns flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(fakeMovies))
            emit(Resource.Loading(false))
        }

        // Act: Create ViewModel (this triggers init block automatically)
        val viewModel = HomeViewModel(movieListRepository)

        // Advance dispatcher to let coroutines run
        advanceUntilIdle()

        // Assert: state reflects loaded data from page 1
        assertEquals(1, viewModel.state.movieList.size)
        assertEquals("Test Movie", viewModel.state.movieList.first().title)
        assertFalse(viewModel.state.isLoading)
        assertEquals(2, viewModel.state.page) // page should increment after success
    }

    @Test
    fun `onEvent(Paginate) loads next page of 3 more movies`() = runTest {
        // Initial page 1 result (called from init)
        val initialMovies = listOf(
            Movie("/backdrop1.jpg", 1, "Original 1", "Overview 1", "/poster1.jpg", "Movie 1"),
            Movie("/backdrop2.jpg", 2, "Original 2", "Overview 2", "/poster2.jpg", "Movie 2"),
            Movie("/backdrop3.jpg", 3, "Original 3", "Overview 3", "/poster3.jpg", "Movie 3")
        )

        // Page 2 result (pagination)
        val nextPageMovies = listOf(
            Movie("/backdrop4.jpg", 4, "Original 4", "Overview 4", "/poster4.jpg", "Movie 4"),
            Movie("/backdrop5.jpg", 5, "Original 5", "Overview 5", "/poster5.jpg", "Movie 5"),
            Movie("/backdrop6.jpg", 6, "Original 6", "Overview 6", "/poster6.jpg", "Movie 6")
        )

        //Mock page 1 (init)
        coEvery {
            movieListRepository.getMovieList(
                needToFetchFromRemote = false,
                personId = ApiConstants.PEOPLE_ID,
                page = 1
            )
        } returns flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(initialMovies))
            emit(Resource.Loading(false))
        }

        // Mock page 2 (pagination)
        coEvery {
            movieListRepository.getMovieList(
                needToFetchFromRemote = true,
                personId = ApiConstants.PEOPLE_ID,
                page = 2
            )
        } returns flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(nextPageMovies))
            emit(Resource.Loading(false))
        }

        // Act: Create ViewModel and wait for init block to complete
        val viewModel = HomeViewModel(movieListRepository)

        // Wait for init block to complete (page = 2)
        advanceUntilIdle()

        // Trigger pagination (should now call for page 2)
        viewModel.onEvent(MovieListUiEvent.Paginate)

        // Wait for pagination to complete (page = 3)
        advanceUntilIdle()

        // Assert: both pages are combined in movieList
        val result = viewModel.state
        assertEquals(6, result.movieList.size)
        assertEquals("Movie 1", result.movieList[0].title)
        assertEquals("Movie 6", result.movieList[5].title)
        assertEquals(3, result.page) // incremented from 2 to 3
        assertFalse(result.isLoading)
    }

    @Test
    fun `init block returns success with null data does not update state`() = runTest {
        // Arrange: Mock API call from init with null data
        coEvery {
            movieListRepository.getMovieList(
                needToFetchFromRemote = false,
                personId = ApiConstants.PEOPLE_ID,
                page = 1
            )
        } returns flow {
            emit(Resource.Loading(true))
            emit(Resource.Success(null)) // null response
            emit(Resource.Loading(false))
        }

        // Act: This triggers the init block
        val viewModel = HomeViewModel(movieListRepository)

        // Let coroutine finish
        advanceUntilIdle()

        // Assert
        val result = viewModel.state
        assertTrue(result.movieList.isEmpty())       // should not add null movies
        assertEquals(1, result.page)         // page should NOT increment
        assertFalse(result.isLoading)                //loading should stop
    }

}