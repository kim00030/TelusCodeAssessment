package com.example.teluscodeassesmentfromdankim.data.remote.repository

import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieDao
import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieDatabase
import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieEntity
import com.example.teluscodeassesmentfromdankim.data.mapper.toMovie
import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.data.remote.MovieApi
import com.example.teluscodeassesmentfromdankim.data.remote.model.MovieDto
import com.example.teluscodeassesmentfromdankim.data.remote.model.MovieListDto
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.utils.Resource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

/**
 * Unit tests for [MovieListRepositoryImpl].
 *
 * This class verifies the behavior of the repository methods that fetch movie data
 * from local Room DB or from the remote TMDB API.
 *
 * Tested Scenarios:
 * - Fetching movie list from local DB when remote fetch is not needed
 * - Fetching movie list from remote API when forced
 * - Fetching a specific movie by ID from local DB (success and failure cases)
 *
 * Note:
 * During test execution, the following red SLF4J log appears:
 * "SLF4J: No SLF4J providers were found. Defaulting to no-operation (NOP) logger implementation"
 * This warning can be safely ignored as it does not affect test results or behavior.
 *
 * Author: Dan Kim
 */
class MovieListRepositoryImplTest {

    private lateinit var movieApi: MovieApi
    private lateinit var movieDao: MovieDao
    private lateinit var movieDatabase: MovieDatabase
    private lateinit var repository: MovieListRepositoryImpl

    private val testDispatcher = StandardTestDispatcher()

    private val fakeMovieEntity = MovieEntity(
        adult = false,
        backdropPath = "/backdrop.jpg",
        genreIds = "28,12",
        id = 1,
        originalLanguage = "en",
        originalTitle = "Fake Movie",
        overview = "A fake movie for testing.",
        popularity = 123.4,
        posterPath = "/poster.jpg",
        releaseDate = "2023-01-01",
        title = "Fake Movie",
        video = false,
        voteAverage = 7.5,
        voteCount = 100
    )

    private val fakeMovie = fakeMovieEntity.toMovie()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieApi = mockk()
        movieDao = mockk()
        movieDatabase = mockk {
            every { movieDao } returns this@MovieListRepositoryImplTest.movieDao
        }

        repository = MovieListRepositoryImpl(movieApi, movieDatabase)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `getMovieList returns local data when available and remote not needed`() = runTest {

        // Arrange — Setup the DAO to return a fake movie list from local DB
        coEvery { movieDao.getMovieList() } returns listOf(fakeMovieEntity)
        // Act — Call the repository method to collect emissions from the flow
        val result = repository.getMovieList(
            needToFetchFromRemote = false, // Set to false to not fetch from remote
            personId = 71580,
            page = 1
        )

        // This list will store each emission from the flow
        val emissions = mutableListOf<Resource<List<Movie>>>()
        // Collect the flow results into the emissions list
        result.collect { emissions.add(it) }

        // Assert — Flow should emit 3 things:
        // 1. Loading(true)
        // 2. Success with local data
        // 3. Loading(false)

        // Check that we received exactly 3 emissions
        assertEquals(3, emissions.size)
        // 1st emission should be Loading(true) — we are starting data load
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue((emissions[0] as Resource.Loading).isLoading)

        // 2nd emission should be Success with the correct fake movie
        assertTrue(emissions[1] is Resource.Success)
        assertEquals(fakeMovie, emissions[1].data?.first())

        // 3rd emission should be Loading(false) — loading is complete
        assertTrue(emissions[2] is Resource.Loading)
        assertFalse((emissions[2] as Resource.Loading).isLoading)
        // Verify — local DB was accessed once
        coVerify(exactly = 1) { movieDao.getMovieList() }
        // Verify — API should NOT be called at all
        coVerify(exactly = 0) { movieApi.getMovies(any(), any()) } // Should NOT be called
    }

    /**
     * Verifies that the repository correctly fetches movies from the remote API
     * when [needToFetchFromRemote] is true.
     *
     * The flow should emit:
     * 1. Resource.Loading(true)
     * 2. Resource.Success(data) with movies from the API (saved to local DB)
     * 3. Resource.Loading(false)
     *
     * This test uses MockK to simulate both the remote API and local database behavior,
     * while only focusing on the control flow and data handling (no real network or DB).
     *
     * NOTE: You may see red SLF4J output in the console:
     *   "No SLF4J providers were found. Defaulting to no-operation (NOP) logger implementation."
     * This is harmless and can be ignored — it's just logging-related and has no impact on test results.
     */
    @Test
    fun `getMovieList fetches from remote when needToFetchFromRemote is true`() = runTest {
        // Arrange — DAO mocks (even if ignored)
        coEvery { movieDao.getMovieList() } returns emptyList()
        coEvery { movieDao.upsertMovieList(any()) } returns Unit

        // Prepare fake MovieEntity (already defined)
        val remoteMovieEntity = listOf(fakeMovieEntity)

        // Create only needed fields for MovieDto
        val remoteMovieDto = remoteMovieEntity.map {
            MovieDto(
                adult = false,
                backdropPath = it.backdropPath,
                genreIds = emptyList(),
                id = it.id,
                originalLanguage = "",
                originalTitle = it.originalTitle,
                overview = it.overview,
                popularity = 0.0,
                posterPath = it.posterPath,
                releaseDate = "",
                title = it.title,
                video = false,
                voteAverage = 0.0,
                voteCount = 0
            )
        }

        // Mock the remote API call with actual parameters (including API key)
        coEvery {
            movieApi.getMovies(
                page = 1,
                personId = 71580,
                apiKey = ApiConstants.API_KEY
            )
        } returns MovieListDto(
            page = 1,
            results = remoteMovieDto,
            totalPages = 1,
            totalResults = 1
        )

        // Act — collect emissions
        val emissions = mutableListOf<Resource<List<Movie>>>()
        repository.getMovieList(
            needToFetchFromRemote = true,
            personId = 71580,
            page = 1
        ).collect { emissions.add(it) }

        // Assert
        assertEquals(3, emissions.size)
        assertTrue(emissions[0] is Resource.Loading && (emissions[0] as Resource.Loading).isLoading)
        assertTrue(emissions[1] is Resource.Success)
        assertEquals(remoteMovieEntity.first().toMovie(), emissions[1].data?.first())
        assertTrue(emissions[2] is Resource.Loading && !(emissions[2] as Resource.Loading).isLoading)

        // Verify mocks
        coVerify(exactly = 1) { movieApi.getMovies(1, 71580, ApiConstants.API_KEY) }
        coVerify(exactly = 1) { movieDao.upsertMovieList(any()) }
    }

    @Test
    fun `getMovie emits error when movie not found in local DB`() = runTest {
        // Arrange — Set up DAO to return null (movie not found)
        coEvery { movieDao.getMovieById(1) } returns null

        // Act — Call the repository method
        val result = repository.getMovie(id = 1)

        // Collect emissions from the flow
        val emissions = mutableListOf<Resource<Movie>>()
        result.collect { emissions.add(it) }

        // Assert — Expect 3 emissions: Loading(true), Error, Loading(false)
        assertEquals(3, emissions.size)

        // 1st emission: Loading starts
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue((emissions[0] as Resource.Loading).isLoading)

        // 2nd emission: Error emitted due to null movie
        assertTrue(emissions[1] is Resource.Error)
        assertEquals("Error on loading movie", emissions[1].message)

        // 3rd emission: Loading ends
        assertTrue(emissions[2] is Resource.Loading)
        assertFalse((emissions[2] as Resource.Loading).isLoading)

        // Verify DAO was called
        coVerify(exactly = 1) { movieDao.getMovieById(1) }
    }

    @Test
    fun `getMovie emits success when movie is found in local DB`() = runTest {
        // Arrange — Stub DAO to return a valid MovieEntity
        coEvery { movieDao.getMovieById(1) } returns fakeMovieEntity

        // Act — Call repository method
        val result = repository.getMovie(id = 1)

        // Collect emissions into a list
        val emissions = mutableListOf<Resource<Movie>>()
        result.collect { emissions.add(it) }

        // Assert — Expect 3 emissions: Loading(true), Success(data), Loading(false)
        assertEquals(3, emissions.size)

        // 1st: Loading(true)
        assertTrue(emissions[0] is Resource.Loading)
        assertTrue((emissions[0] as Resource.Loading).isLoading)

        // 2nd: Success with correct movie
        assertTrue(emissions[1] is Resource.Success)
        assertEquals(fakeMovie, emissions[1].data)

        // 3rd: Loading(false)
        assertTrue(emissions[2] is Resource.Loading)
        assertFalse((emissions[2] as Resource.Loading).isLoading)

        // Verify DAO was queried once
        coVerify(exactly = 1) { movieDao.getMovieById(1) }
    }

}