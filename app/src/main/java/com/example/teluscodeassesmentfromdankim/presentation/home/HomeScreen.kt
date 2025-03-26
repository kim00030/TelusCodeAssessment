package com.example.teluscodeassesmentfromdankim.presentation.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.teluscodeassesmentfromdankim.presentation.common.LoadingIndicator
import com.example.teluscodeassesmentfromdankim.presentation.common.MovieCard

/**
 * Author: Dan Kim
 *
 * @param state State of the screen.
 * @param onNavigateToDetails Callback to navigate to the details screen.
 * @param onEvent Callback to handle UI events.
 */
@Composable
fun HomeScreen(
    state: MovieListState,
    onNavigateToDetails: (Int) -> Unit,
    onEvent: (MovieListUiEvent) -> Unit
) {

    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {

        if (state.movieList.isEmpty()) {
            LoadingIndicator()
        } else {
            LazyColumn(
                modifier = Modifier.padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
            ) {
                items(state.movieList.size) { index ->
                    MovieCard(
                        movie = state.movieList[index],
                        onItemClick = {
                            onNavigateToDetails(state.movieList[index].id)
                        }
                    )
                    // Trigger pagination when the user scrolls to the last visible item
                    // and a new page is not currently being loaded
                    if (index >= state.movieList.size - 1 && !state.isLoading) {
                        onEvent(MovieListUiEvent.Paginate)
                    }
                }
            }
        }
    }
}