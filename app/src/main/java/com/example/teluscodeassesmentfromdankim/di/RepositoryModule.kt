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

@Module
@InstallIn(SingletonComponent::class)
abstract class MovieListRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieListRepositoryImpl: MovieListRepositoryImpl
    ): MovieListRepository
}


@Module
@InstallIn(SingletonComponent::class)
abstract class SimilarMovieListRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSimilarMovieListRepository(
        similarMoviesRepositoryImpl: SimilarMoviesRepositoryImpl
    ): SimilarMoviesRepository
}
