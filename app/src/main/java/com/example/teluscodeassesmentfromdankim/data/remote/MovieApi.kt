package com.example.teluscodeassesmentfromdankim.data.remote

import com.example.teluscodeassesmentfromdankim.data.remote.model.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    //https://api.themoviedb.org/3/discover/movie?with_people=71580&api_key=YOUR_API_KEY

    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("with_people") personId: Int = ApiConstants.PEOPLE_ID,
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ): MovieListDto

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ): MovieListDto

}
