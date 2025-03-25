package com.example.teluscodeassesmentfromdankim.presentation.home

import androidx.annotation.VisibleForTesting
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.domain.repository.MovieListRepository
import com.example.teluscodeassesmentfromdankim.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * HomeViewModel is responsible for managing the state of the Home screen,
 * specifically loading a paginated list of movies featuring **Benedict Cumberbatch**.
 *
 * - On initialization, it fetches the first page of Benedict Cumberbatch's movies from the repository.
 * - Supports pagination via [MovieListUiEvent.Paginate] to load additional pages.
 * - Updates UI state using a [MovieListState] observable property consumed by the Composable UI.
 * - Exposes [setTestState] for unit testing purposes only.
 *
 * This ViewModel uses [MovieListRepository] to fetch movie data,
 * and passes Benedict Cumberbatch's person ID ([ApiConstants.PEOPLE_ID]) as the query parameter.
 *
 * Author: Dan Kim
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    //state observable by UI
    var state by mutableStateOf(MovieListState())
        private set
    // Automatically fetch movies on ViewModel creation
    init {
        getMovieList(false)
    }
    // Handles UI events such as pagination.
    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            MovieListUiEvent.Paginate -> getMovieList(true)
        }
    }
    /**
     * Fetches a page of movies.
     *
     * @param needToFetchFromRemote Determines whether to use remote API or cached local data.
     */
    private fun getMovieList(needToFetchFromRemote: Boolean) {
        viewModelScope.launch {
            state = state.copy(isLoading = true)

            movieListRepository.getMovieList(
                needToFetchFromRemote = needToFetchFromRemote,
                personId = ApiConstants.PEOPLE_ID,
                page = state.page
            ).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {
                        state = state.copy(isLoading = false)
                    }
                    is Resource.Loading -> {
                        state = state.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        // Only update if data is not null
                        result.data?.let { movieList ->
                            state = state.copy(
                                movieList = state.movieList + movieList,
                                page = state.page + 1
                            )
                        }
                    }
                }
            }
        }
    }
    /**
     * Allows test cases to inject a known state.
     * Not used in production.
     */
    @VisibleForTesting
    fun setTestState(newState: MovieListState) {
        state = newState
    }

}