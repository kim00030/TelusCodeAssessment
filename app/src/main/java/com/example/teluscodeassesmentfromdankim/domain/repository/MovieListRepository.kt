package com.example.teluscodeassesmentfromdankim.domain.repository

import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Author: Dan Kim
 *
 * Repository interface for managing movie-related data.
 * Provides access to both remote and local movie sources, supporting features like:
 * - Pagination of movie lists for a specific person (e.g., actor)
 * - Fetching a single movie by ID
 *
 * Implementations can handle caching (e.g., using Room) and remote APIs transparently.
 */
interface MovieListRepository {

    /**
     * Retrieves a paginated list of movies for a given person (e.g., actor).
     *
     * @param needToFetchFromRemote If true, forces data fetch from remote API. Otherwise, uses cached/local data.
     * @param personId The ID of the person (e.g., actor) whose movies are to be retrieved.
     * @param page The page number to fetch for pagination.
     * @return A [Flow] emitting [Resource] with a list of [Movie]s.
     */
    suspend fun getMovieList(
        needToFetchFromRemote: Boolean,
        personId: Int,
        page: Int
    ): Flow<Resource<List<Movie>>>

    /**
     * Retrieves a single movie by its [id].
     *
     * @param id The unique movie ID to retrieve.
     * @return A [Flow] emitting [Resource] containing the [Movie] details.
     */
    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}