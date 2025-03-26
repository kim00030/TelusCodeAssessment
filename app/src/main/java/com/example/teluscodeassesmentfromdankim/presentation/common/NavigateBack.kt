package com.example.teluscodeassesmentfromdankim.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.teluscodeassesmentfromdankim.R

/**
 * Author: Dan Kim
 * re-usable composable for navigating back to the previous screen.
 */
@Composable
fun NavigateBack(
    onNavigateBack: () -> Unit
){
    IconButton(
        modifier = Modifier.padding(start = 8.dp, top = 24.dp, bottom = 32.dp),
        onClick = onNavigateBack,
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back),
            tint = colorResource(R.color.text_title)
        )
    }
}