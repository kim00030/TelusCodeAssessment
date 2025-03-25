package com.example.teluscodeassesmentfromdankim.data.remote.repository

import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieDatabase
import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieEntity
import com.example.teluscodeassesmentfromdankim.data.mapper.toMovie
import com.example.teluscodeassesmentfromdankim.data.mapper.toMovieEntity
import com.example.teluscodeassesmentfromdankim.data.remote.MovieApi
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.domain.repository.MovieListRepository
import com.example.teluscodeassesmentfromdankim.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Implementation of [MovieListRepository] that manages movie data from local and remote sources.
 *
 * Author: Dan Kim
 *
 * @property movieApi Remote API client to fetch movies from the TMDB server.
 * @property movieDatabase Local Room database instance for storing and retrieving cached movies.
 */
class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

    /**
     * Returns a flow of movies. It decides whether to fetch from the remote API or local DB
     * based on [needToFetchFromRemote].
     *
     * - If local data exists and remote fetch is not needed, it returns cached data.
     * - If remote fetch is needed or cache is empty, it fetches from the API,
     *   updates the local DB, and returns fresh data.
     *
     * @param needToFetchFromRemote If true, fetches data from remote regardless of local cache.
     * @param personId TMDB person ID to filter movies (e.g., Benedict Cumberbatch).
     * @param page Page number to support pagination.
     *
     * @return [Flow] emitting [Resource.Loading], [Resource.Success], or [Resource.Error].
     */
    override suspend fun getMovieList(
        needToFetchFromRemote: Boolean,
        personId: Int,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))
            val localMovieList = movieDatabase.movieDao.getMovieList()
            val shouldLoadLocalMovies = localMovieList.isNotEmpty() && !needToFetchFromRemote

            if (shouldLoadLocalMovies) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie()
                    }
                ))
                emit(Resource.Loading(false))
                return@flow
            }

            //remote
            val remoteMovieList = try {
                movieApi.getMovies(personId = personId, page = page)
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error("Can't load movies"))
                return@flow
            }

            val movieEntities = remoteMovieList.results.let { movieDtoList ->
                movieDtoList.map { movieDto ->
                    movieDto.toMovieEntity()
                }
            }
            //store in local
            movieDatabase.movieDao.upsertMovieList(movieEntities)
            emit(Resource.Success(
                data = movieEntities.map { movieEntity ->
                    movieEntity.toMovie()
                }
            ))
            emit(Resource.Loading(false))
        }
    }

    /**
     * Returns a single movie from the local database by its ID.
     *
     * @param id The movie ID to look up.
     *
     * @return [Flow] emitting [Resource.Loading], [Resource.Success] if found,
     * or [Resource.Error] if not found.
     */
    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(isLoading = true))
            val movieEntity: MovieEntity? = movieDatabase.movieDao.getMovieById(id)
            if (movieEntity != null) {
                val movie: Movie = movieEntity.toMovie()
                emit(
                    Resource.Success(data = movie)
                )
                emit(Resource.Loading(isLoading = false))
                return@flow
            }

            emit(Resource.Error(message = "Error on loading movie"))
            emit(Resource.Loading(isLoading = false))
        }
    }
}
