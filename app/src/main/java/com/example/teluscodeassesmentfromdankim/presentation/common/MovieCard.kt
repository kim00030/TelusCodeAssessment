package com.example.teluscodeassesmentfromdankim.presentation.common

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.teluscodeassesmentfromdankim.R
import com.example.teluscodeassesmentfromdankim.data.remote.ApiConstants
import com.example.teluscodeassesmentfromdankim.domain.model.Movie
import com.example.teluscodeassesmentfromdankim.ui.theme.TelusCodeAssesmentFromDanKimTheme

/**
 * Author: Dan Kim
 */

@Composable
fun MovieCard(
    modifier: Modifier = Modifier,
    movie: Movie,
    onItemClick: () -> Unit
) {
    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(ApiConstants.IMAGE_BASE_URL + movie.backdropPath)
            .size(Size.ORIGINAL)
            .crossfade(true)
            .build()
    ).state

    Row(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clip(shape = MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .clickable { onItemClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Box(
            modifier = Modifier
                .width(150.dp)
                .height(100.dp)
                .background(MaterialTheme.colorScheme.primaryContainer)
                .clip(MaterialTheme.shapes.medium)
                .padding(6.dp),
            contentAlignment = Alignment.Center
        ) {
            when (imageState) {

                is AsyncImagePainter.State.Error -> {
                    Icon(
                        imageVector = Icons.Rounded.ImageNotSupported,
                        contentDescription = movie.title,
                        modifier = Modifier
                            .width(150.dp)
                            .height(100.dp)
                    )
                }

                is AsyncImagePainter.State.Success -> {
                    AsyncImage(
                        modifier = Modifier
                            .width(150.dp)
                            .height(100.dp)
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.medium),
                        model = ImageRequest
                            .Builder(LocalContext.current)
                            .data(ApiConstants.IMAGE_BASE_URL + movie.backdropPath)
                            .build(),
                        contentDescription = movie.title,
                        contentScale = ContentScale.Crop
                    )
                }

                else -> {}
            }
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp),
            text = movie.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(R.color.text_title)
        )
    }
}

@Composable
@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun MovieCardPreview() {
    TelusCodeAssesmentFromDanKimTheme {

        val movie = Movie(
            adult = true,
            backdropPath = "/mDfJG3LC3Dqb67AZ52x3Z0jU0uB.jpg",
            genreIds = listOf(1, 2, 3),
            id = 1,
            originalLanguage = "en",
            originalTitle = "Movie Title",
            overview = "As the Avengers and their allies have continued to protect the world from threats too large for any one hero to handle, a new danger has emerged from the cosmic shadows: Thanos. A despot of intergalactic infamy, his goal is to collect all six Infinity Stones, artifacts of unimaginable power, and use them to inflict his twisted will on all of reality. Everything the Avengers have fought for has led up to this moment - the fate of Earth and existence itself has never been more uncertain.",
            popularity = 1.0,
            posterPath = "/7WsyChQLEftFiDOVTGkv3hFpyyt.jpg",
            releaseDate = "2023-01-01",
            title = "Avengers: Infinity War",
            video = true,
            voteAverage = 1.0,
            voteCount = 1
        )
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            MovieCard(
                movie = movie,
                onItemClick = {}
            )
        }
    }
}