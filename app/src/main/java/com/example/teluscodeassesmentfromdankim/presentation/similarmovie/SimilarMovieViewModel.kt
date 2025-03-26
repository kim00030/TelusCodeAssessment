package com.example.teluscodeassesmentfromdankim.presentation.similarmovie

import androidx.annotation.VisibleForTesting
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
 */
@HiltViewModel
class SimilarMoviesViewModel @Inject constructor(
    private val repository: SimilarMoviesRepository
) : ViewModel() {

    var state by mutableStateOf(MovieListState())
        private set

    private var movieId by mutableIntStateOf(-1)

    fun onEvent(event: SimilarMoviesUiEvent) {
        when (event) {

            is SimilarMoviesUiEvent.OnMovieClick -> {}
            is SimilarMoviesUiEvent.FetchMovieId -> {
                movieId = event.movieId
                fetchSimilarMovies(movieId)
            }
        }
    }

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

    @VisibleForTesting
    fun setTestState(newState: MovieListState) {
        state = newState
    }
}
