package com.example.teluscodeassesmentfromdankim.di

import android.app.Application
import androidx.room.Room
import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieDatabase
import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.data.remote.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Author: Dan Kim
 *
 * Provides application-wide dependencies using Dagger-Hilt.
 * This module sets up Retrofit for networking and Room for local database storage.
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Logging interceptor to log full request/response bodies for debugging
    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    // OkHttp client with logging interceptor added
    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    @Provides
    @Singleton
    fun provideMovieApi(): MovieApi {
        return Retrofit
            .Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MovieApi::class.java)
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(application: Application): MovieDatabase {
        return Room.databaseBuilder(
            context = application,
            klass = MovieDatabase::class.java,
            name = "movie_db.db"
        ).build()
    }
}