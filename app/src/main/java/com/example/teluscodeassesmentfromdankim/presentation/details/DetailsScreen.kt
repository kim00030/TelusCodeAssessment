package com.example.teluscodeassesmentfromdankim.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teluscodeassesmentfromdankim.R
import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.presentation.common.LoadingIndicator

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
            IconButton(
                modifier = Modifier.padding(start = 8.dp, top = 24.dp, bottom = 32.dp),
                onClick = onNavigateToHome,
            ) {
                Icon(
                    modifier = Modifier.size(32.dp),
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                    tint = colorResource(R.color.text_title)
                )
            }

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

                Text(
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally).padding(top = 12.dp),
                    text = state.movie.title,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(R.color.text_title),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis

                )

                Spacer(modifier = Modifier.height(30.dp))

                AsyncImage(
                    model = ImageRequest
                        .Builder(LocalContext.current)
                        .data(ApiConstants.IMAGE_BASE_URL + state.movie.posterPath)
                        .build(),
                    contentDescription = state.movie.title + stringResource(R.string.overview),
                    placeholder = painterResource(R.drawable.ic_image_place_holder),
                    error = painterResource(R.drawable.ic_image_place_holder),
                    modifier = Modifier
                        .width(120.dp)
                        .height(200.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(30.dp))
                Text(
                    text = stringResource(R.string.movie_overview),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = colorResource(R.color.text_title),
                    modifier = Modifier.align(Alignment.Start)
                )
                Text(
                    text = state.movie.overview,
                    style = MaterialTheme.typography.bodyMedium,
                    color = colorResource(R.color.body),
                    modifier = Modifier.align(Alignment.Start)
                )
            }
        }
    }
}