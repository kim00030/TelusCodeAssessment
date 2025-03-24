package com.example.teluscodeassesmentfromdankim.presentation.home

sealed class MovieListUiEvent {
    data object NavigateToMovieDetails : MovieListUiEvent()
    data object LoadMovieList: MovieListUiEvent()
}