package com.example.teluscodeassesmentfromdankim.presentation.similarmovie

sealed class SimilarMoviesUiEvent {
    data class FetchMovieId(val movieId: Int) : SimilarMoviesUiEvent()
}
