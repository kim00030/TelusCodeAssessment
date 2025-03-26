package com.example.teluscodeassesmentfromdankim.data.remote.repository

import com.example.teluscodeassesmentfromdankim.data.mapper.toMovie
import com.example.teluscodeassesmentfromdankim.data.remote.MovieApi
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.domain.repository.SimilarMoviesRepository
import com.example.teluscodeassesmentfromdankim.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Author: Dan Kim
 */
class SimilarMoviesRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : SimilarMoviesRepository {

    override fun getSimilarMovies(movieId: Int): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            try {
                val dto = movieApi.getSimilarMovies(movieId)
                val movies = dto.results.map {
                    it.toMovie()
                }
                emit(Resource.Success(movies))
            } catch (e: Exception) {
                emit(Resource.Error("Failed to load similar movies: ${e.message}"))
            }

            emit(Resource.Loading(false))
        }
    }
}