package com.example.teluscodeassesmentfromdankim.presentation.home

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

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository
) : ViewModel() {

    var state by mutableStateOf(MovieListState())
        private set

    init {
        getMovieList(false)
    }

    fun onEvent(event: MovieListUiEvent) {
        when (event) {
            is MovieListUiEvent.NavigateToMovieDetails -> {
                //TODO("Not yet implemented")
            }

            MovieListUiEvent.LoadMovieList -> {
                getMovieList(true)
            }
        }
    }

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

}