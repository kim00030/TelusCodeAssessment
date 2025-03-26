package com.example.teluscodeassesmentfromdankim.presentation.similarmovie

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.teluscodeassesmentfromdankim.R
import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.presentation.common.CustomAsyncImage
import com.example.teluscodeassesmentfromdankim.presentation.home.MovieListState

/**
 * Author: Dan Kim
 *
 * Displays a horizontally scrollable list (LazyRow) of similar movies.
 * When a movie is clicked, a fullscreen dialog appears showing its details.
 *
 * @param similarMoviesState Holds the list of similar movies and loading state.
 */
@Composable
fun SimilarMoviesList(
    similarMoviesState: MovieListState
) {
    // Holds the movie currently selected (clicked) for dialog display
    var selectedMovie by rememberSaveable { mutableStateOf<Movie?>(null) }

    LazyRow(
        contentPadding = PaddingValues(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        items(similarMoviesState.movieList) { movie ->
            Column(
                modifier = Modifier
                    .width(120.dp)
                    .clickable {
                        selectedMovie = movie
                    }
            ) {

                CustomAsyncImage(
                    modifier = Modifier
                        .height(180.dp)
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.small),
                    data = ApiConstants.IMAGE_BASE_URL + movie.backdropPath,
                    contentDescription = movie.title,
                    placeholder = painterResource(R.drawable.ic_image_place_holder),
                    error = painterResource(R.drawable.ic_image_place_holder),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.bodySmall,
                    color = colorResource(R.color.text_title),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
// If a movie was selected, show a fullscreen dialog with its details
    selectedMovie?.let { movie ->
        SimilarMoviesDialog(
            movie = movie,
            onDismiss = { selectedMovie = null }
        )
    }
}