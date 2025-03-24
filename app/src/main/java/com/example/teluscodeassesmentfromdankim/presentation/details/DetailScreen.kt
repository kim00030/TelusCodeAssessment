package com.example.teluscodeassesmentfromdankim.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Author: Dan Kim
 */

@Composable
fun DetailScreen(
    id: Int = 20,
    onNavigateToHome: () -> Unit
){
    Column(modifier = Modifier
        .fillMaxSize().background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )  {

        Text(text = "Detail Screen->ID:$id")
        Button(
            onClick = { onNavigateToHome() },
        ) {
            Text(text = "Go to Home")
        }
    }
}