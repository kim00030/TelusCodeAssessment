package com.example.teluscodeassesmentfromdankim.domain.repository

import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Author: Dan Kim
 */
interface SimilarMoviesRepository {
    fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>>
}