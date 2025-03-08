package com.meronacompany.feature.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import timber.log.Timber

@Composable
fun DetailScreen(movieId: String) {
    Timber.d("DetailScreen: $movieId")

    Scaffold(
        topBar = {},
        content = { paddingValues ->
            DetailContent(paddingValues)
        },
        bottomBar = { }
    )
}

@Composable
fun DetailContent(paddingValues: PaddingValues) {
    // Detail Content
    Text(text = "Detail Screen")
}