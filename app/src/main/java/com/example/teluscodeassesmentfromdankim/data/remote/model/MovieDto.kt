package com.example.teluscodeassesmentfromdankim.data.remote.model


import com.google.gson.annotations.SerializedName

/**
 * Data Transfer Object (DTO) representing a movie item received from TMDB API responses
 * (e.g., `discover/movie`, `movie/{movie_id}/similar`, etc.).
 *
 * All fields are nullable to safely handle missing data from the API.
 *
 * This class is mapped from the JSON response using Gson and used before conversion
 * to domain or database models (e.g., [Movie], [MovieEntity]).
 */
data class MovieDto(

    val adult: Boolean?,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("genre_ids")
    val genreIds: List<Int>?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("original_language")
    val originalLanguage: String?,
    @SerializedName("original_title")
    val originalTitle: String?,
    val overview: String?,
    val popularity: Double?,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String?,
    val video: Boolean?,
    @SerializedName("vote_average")
    val voteAverage: Double?,
    @SerializedName("vote_count")
    val voteCount: Int?
)