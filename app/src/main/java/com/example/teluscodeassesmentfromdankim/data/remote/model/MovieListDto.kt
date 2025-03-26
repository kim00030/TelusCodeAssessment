package com.example.teluscodeassesmentfromdankim.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) representing the response from the TMDB movie list endpoints,
 * including both `discover/movie` and `movie/{movie_id}/similar`.
 *
 * This class is used for parsing the JSON response using Gson.
 *
 * @property page Current page number of the results.
 * @property results List of [MovieDto] items representing individual movies.
 * @property totalPages Total number of pages available for the request.
 * @property totalResults Total number of results matching the query.
 */
data class MovieListDto(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MovieDto>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)