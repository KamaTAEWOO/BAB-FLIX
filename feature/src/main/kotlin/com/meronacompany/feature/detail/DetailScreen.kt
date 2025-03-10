package com.meronacompany.feature.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.meronacompany.design.common.YoutubePlayer
import com.meronacompany.feature.home.HomeState
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.home.model.MovieDetailItem
import kotlinx.coroutines.delay
import timber.log.Timber

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailScreen(homeViewModel: HomeViewModel, movieId: String) {
    val homeUiState = homeViewModel.uiState.value
    val movieKey = movieId.toIntOrNull()
    var movieDetailItem: MovieDetailItem? = null

    LaunchedEffect("Unit") {
        homeUiState.allPopularMoviesData.values.forEach { value ->
            value.forEach { movieItem ->
                if (movieItem.id == movieKey) {
                    movieDetailItem = MovieDetailItem(
                        adult = movieItem.adult,
                        backdrop_path = movieItem.backdrop_path,
                        genre_ids = movieItem.genre_ids,
                        id = movieItem.id,
                        original_language = movieItem.original_language,
                        original_title = movieItem.original_title,
                        overview = movieItem.overview,
                        popularity = movieItem.popularity,
                        poster_path = movieItem.poster_path,
                        release_date = movieItem.release_date,
                        title = movieItem.title,
                        video = movieItem.video,
                        vote_average = movieItem.vote_average,
                        vote_count = movieItem.vote_count
                    )
                }
            }
        }

        if (movieKey != null) {
            homeViewModel.requestMovieVideo(movieKey)
        }

        delay(1000)
    }

    Scaffold(
        topBar = {},
        content = { paddingValues ->
            DetailContent(
                paddingValues = paddingValues,
                movieDetailItem = movieDetailItem,
                homeUiState = homeUiState)
        },
        bottomBar = { }
    )
}

@Composable
fun DetailContent(paddingValues: PaddingValues, movieDetailItem: MovieDetailItem?, homeUiState: HomeState) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ){
        if (homeUiState.movieVideoKey != null) {
            Timber.d("movieVideoKey: ${homeUiState.movieVideoKey}")
            YoutubePlayer(videoId = homeUiState.movieVideoKey)
        }
    }
}