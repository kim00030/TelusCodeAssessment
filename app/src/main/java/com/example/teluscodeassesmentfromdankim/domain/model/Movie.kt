package com.example.teluscodeassesmentfromdankim.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Author: Dan Kim
 *
 * Represents a simplified Movie model used in the presentation layer.
 * This class is designed to hold only the essential data required by the UI.
 *
 * Many fields from the full TMDB API response (e.g., `adult`, `genreIds`, `voteAverage`) are intentionally
 * excluded or commented out to keep the UI model lightweight. They can be re-enabled later if needed.
 */
data class Movie(
//    val adult: Boolean,
//    @SerializedName("backdrop_path")
    val backdropPath: String,
//    @SerializedName("genre_ids")
//    val genreIds: List<Int>,
    val id: Int,
//    @SerializedName("original_language")
//    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
//    val popularity: Double,
//    @SerializedName("poster_path")
    val posterPath: String,
//    @SerializedName("release_date")
//    val releaseDate: String,
    val title: String,
    //val video: Boolean,
//    @SerializedName("vote_average")
//    val voteAverage: Double,
//    @SerializedName("vote_count")
//    val voteCount: Int
)
