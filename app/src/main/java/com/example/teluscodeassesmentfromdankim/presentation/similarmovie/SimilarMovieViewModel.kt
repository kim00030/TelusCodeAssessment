package com.example.teluscodeassesmentfromdankim.presentation.similarmovie

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teluscodeassesmentfromdankim.domain.repository.SimilarMoviesRepository
import com.example.teluscodeassesmentfromdankim.presentation.home.MovieListState
import com.example.teluscodeassesmentfromdankim.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Dan Kim
 *
 * ViewModel responsible for managing and exposing the state of similar movies.
 *
 * This ViewModel fetches a list of similar movies using the given movie ID.
 * The result is stored in a shared MovieListState, which contains the loading status
 * and a list of Movie objects.
 *
 * This ViewModel:
 * - Uses `SimilarMoviesRepository` to fetch similar movies from the network.
 * - Stores the result in a `MovieListState`.
 * - Exposes a public `onEvent()` function to trigger fetching.
 *
 * Note: Does not use a local database for caching similar movies, as this is
 * a temporary screen-specific feature.
 */
@HiltViewModel
class SimilarMoviesViewModel @Inject constructor(
    private val repository: SimilarMoviesRepository
) : ViewModel() {

    // UI state containing loading flag and similar movies
    var state by mutableStateOf(MovieListState())
        private set

    // Holds current movie ID used for API call
    private var movieId by mutableIntStateOf(-1)

    /**
     * Handles UI events for this ViewModel.
     *
     * Currently supports:
     * - FetchMovieId: Triggers loading of similar movies
     */
    fun onEvent(event: SimilarMoviesUiEvent) {
        when (event) {
            is SimilarMoviesUiEvent.FetchMovieId -> {
                movieId = event.movieId
                fetchSimilarMovies(movieId)
            }
        }
    }

    /**
     * Triggers a network call to fetch similar movies using the provided movie ID
     */
    private fun fetchSimilarMovies(movieId: Int) {
        viewModelScope.launch {
            repository.getSimilarMovies(movieId).collectLatest { result ->
                when (result) {
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        result.data?.let { movieList ->
                            state = state.copy(
                                movieList = movieList,
                                isLoading = false
                            )
                        }
                    }

                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                        // optional: handle error state
                    }
                }
            }
        }
    }
}
