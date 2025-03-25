package com.example.teluscodeassesmentfromdankim.presentation.home

sealed class MovieListUiEvent {
    data object Paginate : MovieListUiEvent()
}