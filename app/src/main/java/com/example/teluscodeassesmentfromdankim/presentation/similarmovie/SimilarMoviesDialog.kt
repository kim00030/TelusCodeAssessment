package com.example.teluscodeassesmentfromdankim.presentation.similarmovie

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.example.teluscodeassesmentfromdankim.R
import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.presentation.common.CustomAsyncImage
import com.example.teluscodeassesmentfromdankim.presentation.common.NavigateBack

/**
 * A full-screen dialog displaying the details of a similar movie.
 *
 * This composable is used when a user selects a similar movie from the LazyRow on the Detail screen.
 * It overlays the current screen and displays the movie's title, poster image, and overview.
 *
 * @param movie The [Movie] object representing the selected similar movie.
 * @param onDismiss Callback to close the dialog.
 *
 * Author: Dan Kim
 */
@Composable
fun SimilarMoviesDialog(
    movie: Movie,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false) // Fullscreen!
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            NavigateBack(onNavigateBack = onDismiss)

            val scrollState = rememberScrollState()

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 64.dp)
                    .verticalScroll(scrollState),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Movie title
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(R.color.text_title),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
                // Movie poster image
                CustomAsyncImage(
                    modifier = Modifier
                        .width(140.dp)
                        .height(220.dp)
                        .clip(MaterialTheme.shapes.medium),
                    data = ApiConstants.IMAGE_BASE_URL + movie.posterPath,
                    contentDescription = movie.title,
                    placeholder = painterResource(R.drawable.ic_image_place_holder),
                    error = painterResource(R.drawable.ic_image_place_holder),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(24.dp))
                // Overview header
                Text(
                    text = stringResource(R.string.movie_overview),
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(R.color.text_title),
                    modifier = Modifier.align(Alignment.Start)
                )
                // Overview content
                Text(
                    text = movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.body),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }
    }
}
