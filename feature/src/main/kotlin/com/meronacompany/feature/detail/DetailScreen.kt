package com.meronacompany.feature.detail

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.navigation.NavRouteLabel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailScreen(homeViewModel: HomeViewModel, movieId: String, route: String) {
    val homeUiState = homeViewModel.uiState.collectAsState().value

    LaunchedEffect(movieId) {
        if (homeUiState.movieVideoKey != movieId) {
            homeViewModel.setLoading(true) // 로딩 시작
            homeViewModel.requestMovieVideo(movieId.toInt())
            homeViewModel.requestMovieDetail(movieId.toInt())
            homeViewModel.requestMovieCertification(movieId.toInt())
            homeViewModel.requestMovieCredits(movieId.toInt())
            homeViewModel.setLoading(false) // 로딩 종료
        }
    }

    Scaffold(
        topBar = {},
        content = { paddingValues ->
            if (homeViewModel.isLoading.value) {
                Text(text = "Loading...")
            } else {
                val detailMovieUIModel = detailMovieContentData(homeUiState)
                val detailTvUIModel = detailTvContentData(homeUiState)

                if (route == NavRouteLabel.MOVIE) {
                    DetailMovieContent(
                        paddingValues = paddingValues,
                        homeUiState = homeUiState,
                        movieId = movieId,
                        detailUIModel = detailMovieUIModel
                    )
                } else {
                    DetailTvContent(
                        paddingValues = paddingValues,
                        homeUiState = homeUiState,
                        movieId = movieId,
                        detailUIModel = detailTvUIModel
                    )
                }
            }
        },
        bottomBar = {}
    )
}
