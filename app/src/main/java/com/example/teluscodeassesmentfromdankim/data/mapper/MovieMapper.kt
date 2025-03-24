package com.example.teluscodeassesmentfromdankim.data.mapper

import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieEntity
import com.example.teluscodeassesmentfromdankim.data.remote.model.MovieDto
import com.example.teluscodeassesmentfromdankim.domain.model.Movie

fun MovieDto.toMovieEntity(): MovieEntity {
    return MovieEntity(
        adult = this.adult ?: false,
        backdropPath = this.backdropPath ?: "",
        genreIds = try {
            this.genreIds?.joinToString(",") ?: ""
        } catch (e: Exception) {
            ""
        },
        id = this.id ?: -1,
        originalLanguage = this.originalLanguage ?: "",
        originalTitle = this.originalTitle ?: "",
        overview = this.overview ?: "",
        popularity = this.popularity ?: 0.0,
        posterPath = this.posterPath ?: "",
        releaseDate = this.releaseDate ?: "",
        title = this.title ?: "",
        video = this.video ?: false,
        voteAverage = this.voteAverage ?: 0.0,
        voteCount = this.voteCount ?: 0
    )
}


fun MovieEntity.toMovie(): Movie {
    return Movie(
        adult = this.adult,
        backdropPath = this.backdropPath,
        genreIds = try {
            this.genreIds.split(",").map { it.toInt() }
        } catch (e: Exception) {
            emptyList()
        },
        id = this.id,
        originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        popularity = this.popularity,
        posterPath = this.posterPath,
        releaseDate = this.releaseDate,
        title = this.title,
        video = this.video,
        voteAverage = this.voteAverage,
        voteCount = this.voteCount
    )
}