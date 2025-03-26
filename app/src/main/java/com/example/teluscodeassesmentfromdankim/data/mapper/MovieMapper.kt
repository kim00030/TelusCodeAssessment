package com.example.teluscodeassesmentfromdankim.data.mapper

import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieEntity
import com.example.teluscodeassesmentfromdankim.data.remote.model.MovieDto
import com.example.teluscodeassesmentfromdankim.domain.model.Movie

/**
 * Maps a [MovieDto] (from remote API) to a [MovieEntity] (for local Room storage).
 *
 * - Handles null values by applying sensible defaults:
 *   - Strings default to `""`
 *   - Booleans default to `false`
 *   - Numbers default to `-1` or `0.0`
 *   - `genreIds` list is joined as a comma-separated string (e.g., "28,12,878")
 * - Catches exceptions during genre ID conversion to avoid crashing on bad data.
 */
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

/**
 * Maps a [MovieEntity] (from local Room DB) to a domain [Movie] model.
 *
 * This mapper is designed to:
 * - Provide only the fields required for UI presentation
 * - Avoid unnecessary parsing (e.g., skips genre ID conversion)
 *
 * Note: Some fields like `adult`, `language`, `video`, and `voteAverage`
 * are commented out if not needed in the UI layer to keep the object lightweight.
 */
fun MovieEntity.toMovie(): Movie {
    return Movie(
        //adult = this.adult,
        backdropPath = this.backdropPath,
//        genreIds = try {
//            this.genreIds.split(",").map { it.toInt() }
//        } catch (e: Exception) {
//            emptyList()
//        },
        id = this.id,
        //originalLanguage = this.originalLanguage,
        originalTitle = this.originalTitle,
        overview = this.overview,
        //popularity = this.popularity,
        posterPath = this.posterPath,
        //releaseDate = this.releaseDate,
        title = this.title,
        //video = this.video,
        //voteAverage = this.voteAverage,
        //voteCount = this.voteCount
    )
}

/**
 * Maps a [MovieDto] (from local Room DB) to a domain [Movie] model.
 */
fun MovieDto.toMovie(): Movie {
    return Movie(
        backdropPath = this.backdropPath ?: "",
        id = this.id ?: -1,
        originalTitle = this.originalTitle ?: "",
        overview = this.overview ?: "",
        posterPath = this.posterPath ?: "",
        title = this.title ?: ""
    )
}