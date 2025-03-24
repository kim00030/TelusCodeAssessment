package com.example.teluscodeassesmentfromdankim.domain.repository

import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    suspend fun getMovieList(
        needToFetchFromRemote: Boolean,
        personId: Int,
        page: Int
    ): Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}