package com.example.teluscodeassesmentfromdankim.data.remote

import com.example.teluscodeassesmentfromdankim.data.remote.model.MovieListDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    /**
     * Fetches a paginated list of movies associated with a specific person (e.g., Benedict Cumberbatch).
     *
     * Example:
     * https://api.themoviedb.org/3/discover/movie?with_people=71580&api_key=YOUR_API_KEY
     *
     * @param page The page number for pagination.
     * @param personId The TMDB person ID (defaults to Benedict Cumberbatch).
     * @param apiKey The API key used for authentication (default from [ApiConstants]).
     *
     * @return A [MovieListDto] containing a list of movies.
     */
    @GET("discover/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("with_people") personId: Int = ApiConstants.PEOPLE_ID,
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ): MovieListDto

    /**
     * Fetches a list of movies that are similar to the given movie ID.
     *
     * Example:
     * https://api.themoviedb.org/3/movie/{movie_id}/similar?api_key=YOUR_API_KEY
     *
     * @param movieId The ID of the movie to find similar movies for.
     * @param apiKey The API key used for authentication (default from [ApiConstants]).
     *
     * @return A [MovieListDto] containing similar movies.
     */
    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = ApiConstants.API_KEY
    ): MovieListDto

}
