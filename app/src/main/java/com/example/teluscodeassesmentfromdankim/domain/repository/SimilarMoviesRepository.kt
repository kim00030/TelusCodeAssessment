package com.example.teluscodeassesmentfromdankim.domain.repository

import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Author: Dan Kim
 *
 * Repository interface responsible for fetching a list of movies
 * that are similar to a given movie, based on the provided movie ID.
 *
 * This repository abstracts the data source (remote API) for similar movies
 * and emits a stream of [Resource] that can represent loading, success, or error states.
 */
interface SimilarMoviesRepository {

    /**
     * Fetches similar movies for the given [movieId].
     *
     * @param movieId The ID of the movie for which to find similar titles.
     * @return A [Flow] emitting [Resource] containing a list of similar [Movie]s.
     */
    fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>>
}