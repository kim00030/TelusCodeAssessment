package com.example.teluscodeassesmentfromdankim.data.mapper

import com.example.teluscodeassesmentfromdankim.data.local.moviedb.MovieEntity
import com.example.teluscodeassesmentfromdankim.data.remote.model.MovieDto
import org.junit.Assert.*
import org.junit.Test

/**
 * Author: Dan Kim
 *
 * Unit tests for movie mappers:
 * - MovieDto.toMovieEntity()
 * - MovieEntity.toMovie()
 *
 * Ensures accurate conversion from remote data to local DB entities,
 * and from DB entities to UI-friendly domain models.
 */
class MovieMapperKtTest {
    @Test
    fun `toMovieEntity correctly maps non-null and null fields`() {
        val dto = MovieDto(
            adult = null,
            backdropPath = null,
            genreIds = listOf(28, 12, 878),
            id = null,
            originalLanguage = null,
            originalTitle = "Original Title",
            overview = "Overview here",
            popularity = null,
            posterPath = "/poster.jpg",
            releaseDate = null,
            title = "Movie Title",
            video = null,
            voteAverage = null,
            voteCount = null
        )

        val entity = dto.toMovieEntity()

        assertFalse(entity.adult) // default false
        assertEquals("", entity.backdropPath) // default ""
        assertEquals("28,12,878", entity.genreIds)
        assertEquals(-1, entity.id) // default -1
        assertEquals("", entity.originalLanguage)
        assertEquals("Original Title", entity.originalTitle)
        assertEquals("Overview here", entity.overview)
        assertEquals(0.0, entity.popularity, 0.01)
        assertEquals("/poster.jpg", entity.posterPath)
        assertEquals("", entity.releaseDate)
        assertEquals("Movie Title", entity.title)
        assertFalse(entity.video)
        assertEquals(0.0, entity.voteAverage, 0.01)
        assertEquals(0, entity.voteCount)
    }

    @Test
    fun `toMovie maps only the required UI fields from MovieEntity`() {
        val entity = MovieEntity(
            adult = true,
            backdropPath = "/backdrop.jpg",
            genreIds = "28,12",
            id = 123,
            originalLanguage = "en",
            originalTitle = "Original Title",
            overview = "A great movie",
            popularity = 100.0,
            posterPath = "/poster.jpg",
            releaseDate = "2023-01-01",
            title = "Movie Title",
            video = true,
            voteAverage = 8.5,
            voteCount = 1000
        )

        val movie = entity.toMovie()

        assertEquals(123, movie.id)
        assertEquals("/backdrop.jpg", movie.backdropPath)
        assertEquals("Original Title", movie.originalTitle)
        assertEquals("A great movie", movie.overview)
        assertEquals("/poster.jpg", movie.posterPath)
        assertEquals("Movie Title", movie.title)
    }
}