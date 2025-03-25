package com.example.teluscodeassesmentfromdankim.presentation.details

import com.example.teluscodeassesmentfromdankim.domain.model.Movie

/**
 * Author: Dan Kim
 * @property isLoading Indicates whether the screen is currently loading.
 * @property movie The movie data to be displayed.
 */
data class DetailsState(
    val isLoading: Boolean = false,
    val movie: Movie? = null
)
