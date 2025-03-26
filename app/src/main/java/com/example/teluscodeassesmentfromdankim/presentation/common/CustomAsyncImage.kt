package com.example.teluscodeassesmentfromdankim.presentation.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

/**
 * Author: Dan Kim
 */
@Composable
fun CustomAsyncImage(
    modifier: Modifier,
    data: Any?,
    contentDescription: String? = null,
    contentScale: ContentScale? = null,
    placeholder: Painter? = null,
    error: Painter? = null,
) {

    AsyncImage(
        modifier = modifier,
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(data)
            .build(),
        contentDescription = contentDescription,
        placeholder = placeholder,
        error = error,
        contentScale = contentScale ?: ContentScale.Crop
    )
}