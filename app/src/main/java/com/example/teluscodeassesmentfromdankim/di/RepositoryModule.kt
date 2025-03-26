package com.example.teluscodeassesmentfromdankim.di

import com.example.teluscodeassesmentfromdankim.data.remote.repository.MovieListRepositoryImpl
import com.example.teluscodeassesmentfromdankim.data.remote.repository.SimilarMoviesRepositoryImpl
import com.example.teluscodeassesmentfromdankim.domain.repository.MovieListRepository
import com.example.teluscodeassesmentfromdankim.domain.repository.SimilarMoviesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Author: Dan Kim
 *
 * Dagger-Hilt module for providing a singleton instance of [MovieListRepository].
 * This binds the implementation [MovieListRepositoryImpl] to the interface [MovieListRepository].
 *
 * This is used for movie list retrieval, including both caching from Room DB and fetching from the remote API.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MovieListRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository
}

/**
 * Author: Dan Kim
 *
 * Dagger-Hilt module for providing a singleton instance of [SimilarMoviesRepository].
 * This binds the implementation [SimilarMoviesRepositoryImpl] to the interface [SimilarMoviesRepository].
 *
 * This repository is responsible for fetching similar movies from the remote TMDB API.
 * No Room DB caching is involved in this repository.
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class SimilarMovieListRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSimilarMovieListRepository(
        similarMoviesRepositoryImpl: SimilarMoviesRepositoryImpl
    ): SimilarMoviesRepository
}
