package com.example.teluscodeassesmentfromdankim.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.teluscodeassesmentfromdankim.R
import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.presentation.common.CustomAsyncImage
import com.example.teluscodeassesmentfromdankim.presentation.common.LoadingIndicator
import com.example.teluscodeassesmentfromdankim.presentation.common.NavigateBack
import com.example.teluscodeassesmentfromdankim.presentation.home.MovieListState
import com.example.teluscodeassesmentfromdankim.presentation.similarmovie.SimilarMoviesList

/**
 * Author: Dan Kim
 * Designed as per the requirements
 * Even though the design does not show back button, I added it for the sake of navigation
 *
 * @param state State of the screen.
 * @param onNavigateToHome Callback to navigate back to the home screen.
 */
@Composable
fun DetailScreen(
    state: DetailsState,
    similarMoviesState: MovieListState,
    onNavigateToHome: () -> Unit
) {
    if (state.movie == null) {
        LoadingIndicator()
    } else {

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            NavigateBack(onNavigateBack = onNavigateToHome)

            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding()
                    .padding(32.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {

                //Movie Title
                Text(
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = 12.dp),
                    text = state.movie.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(R.color.text_title),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(30.dp))
                //Movie poster
                CustomAsyncImage(
                    modifier = Modifier
                        .width(120.dp)
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.small),
                    data = ApiConstants.IMAGE_BASE_URL + state.movie.posterPath,
                    contentDescription = state.movie.title + stringResource(R.string.overview),
                    placeholder = painterResource(R.drawable.ic_image_place_holder),
                    error = painterResource(R.drawable.ic_image_place_holder),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(30.dp))
                //Overview heading
                Text(
                    text = stringResource(R.string.movie_overview),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(R.color.text_title),
                    modifier = Modifier.align(Alignment.Start)
                )
                //Overview
                Text(
                    text = state.movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.body),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // LazyRow for similar movies
                if (similarMoviesState.movieList.isNotEmpty()) {

                    Text(
                        text = stringResource(R.string.similar_movies),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = colorResource(R.color.text_title),
                        modifier = Modifier.align(Alignment.Start),
                        textDecoration = TextDecoration.Underline
                    )

                    SimilarMoviesList(similarMoviesState)
                }
            }

        }
    }
}