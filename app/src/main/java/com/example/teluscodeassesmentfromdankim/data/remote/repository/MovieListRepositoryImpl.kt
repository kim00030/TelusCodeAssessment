package com.example.teluscodeassesmentfromdankim.data.remote.repository

import coil.network.HttpException
import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieDatabase
import com.example.teluscodeassesmentfromdankim.data.mapper.toMovie
import com.example.teluscodeassesmentfromdankim.data.mapper.toMovieEntity
import com.example.teluscodeassesmentfromdankim.data.remote.MovieApi
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.domain.repository.MovieListRepository
import com.example.teluscodeassesmentfromdankim.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : MovieListRepository {

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
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Can't load movies"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Can't load movies"))
                return@flow
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
            movieDatabase.movieDao.upsertMovieList(movieEntities)
            emit(Resource.Success(
                data = movieEntities.map { movieEntity ->
                    movieEntity.toMovie()
                }
            ))
            emit(Resource.Loading(false))
        }

    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        TODO("Not yet implemented")
    }
}