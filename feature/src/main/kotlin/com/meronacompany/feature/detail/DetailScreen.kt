package com.meronacompany.feature.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.meronacompany.design.common.YoutubePlayer
import com.meronacompany.feature.home.HomeState
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.home.model.MovieDetailItem
import timber.log.Timber

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailScreen(homeViewModel: HomeViewModel, movieId: String) {
    val homeUiState = homeViewModel.uiState.collectAsState().value

    // LaunchedEffect will be triggered only when `movieKey` changes
    LaunchedEffect(movieId) {
        // Request movie video only if movieKey is valid and not already requested
        if (homeUiState.movieVideoKey != movieId) {
            homeViewModel.requestMovieVideo(movieId.toInt())
        }
    }

    Scaffold(
        topBar = {},
        content = { paddingValues ->
            DetailContent(
                paddingValues = paddingValues,
                homeUiState = homeUiState,
                movieId = movieId
            )
        },
        bottomBar = {}
    )
}

@Composable
fun DetailContent(paddingValues: PaddingValues, homeUiState: HomeState, movieId: String?) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Show YouTube player only if movieVideoKey is available
        if (homeUiState.movieVideoKey != null && homeUiState.movieVideoKey != movieId) {
            Timber.d("movieVideoKey: ${homeUiState.movieVideoKey}")
            YoutubePlayer(videoId = homeUiState.movieVideoKey)
        }
    }
}