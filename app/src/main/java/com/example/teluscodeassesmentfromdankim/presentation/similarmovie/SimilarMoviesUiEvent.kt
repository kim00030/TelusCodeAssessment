package com.example.teluscodeassesmentfromdankim.presentation.similarmovie

import com.example.teluscodeassesmentfromdankim.domain.model.Movie

/**
 * Author: Dan Kim
 */
sealed class SimilarMoviesUiEvent {
    data class OnMovieClick(val movie: Movie) : SimilarMoviesUiEvent()
    data class FetchMovieId(val movieId: Int) : SimilarMoviesUiEvent()
}
