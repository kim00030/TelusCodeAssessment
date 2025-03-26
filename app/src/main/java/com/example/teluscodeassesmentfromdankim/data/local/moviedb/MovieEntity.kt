package com.example.teluscodeassesmentfromdankim.data.local.moviedb

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
/**
 * Author: Dan Kim
 *
 * Represents a movie entity stored in the local Room database.
 * This model is used for caching and offline access.
 **/
@Entity
data class MovieEntity(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: String,
    @PrimaryKey val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)