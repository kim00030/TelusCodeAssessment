package com.example.teluscodeassesmentfromdankim.presentation.home

import com.example.teluscodeassesmentfromdankim.domain.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val page: Int = 1,
    val movieList: List<Movie> = emptyList()
)
