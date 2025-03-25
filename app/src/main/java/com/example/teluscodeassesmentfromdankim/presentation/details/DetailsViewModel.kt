package com.example.teluscodeassesmentfromdankim.presentation.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teluscodeassesmentfromdankim.domain.repository.MovieListRepository
import com.example.teluscodeassesmentfromdankim.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Author: Dan Kim
 *
 * @property movieListRepository Repository for fetching movie data.
 * @property savedStateHandle State handle to retrieve Movie ID passed by Home screen.
 */
@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int = checkNotNull(savedStateHandle[DetailsConstants.KEY_MOVIE_ID])
    var state by mutableStateOf(DetailsState())
        private set

    init {
        getMovieDetails()
    }

    private fun getMovieDetails() {
        viewModelScope.launch {
            state = state.copy(isLoading = true)
            movieListRepository.getMovie(movieId).collectLatest { result ->

                when (result) {
                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                    }

                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }

                    is Resource.Success -> {
                        result.data?.let { movie ->
                            state = state.copy(isLoading = false, movie = movie)
                        }
                    }
                }
            }
        }
    }
}