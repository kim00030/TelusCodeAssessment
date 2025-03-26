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
 *
 * Implementation of [SimilarMoviesRepository] that fetches similar movies
 * from the TMDB API and emits results as a Flow of [Resource].
 *
 * This class does not interact with any local database. It is designed for
 * stateless, on-demand API calls for similar movie suggestions.
 */
class SimilarMoviesRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi
) : SimilarMoviesRepository {

    /**
     * Retrieves a list of movies similar to the given [movieId] from the TMDB API.
     * Emits loading state, then success or error as a [Resource] wrapper.
     *
     * @param movieId The ID of the movie to find similar recommendations for.
     * @return A [Flow] emitting [Resource.Loading], [Resource.Success], or [Resource.Error].
     */
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