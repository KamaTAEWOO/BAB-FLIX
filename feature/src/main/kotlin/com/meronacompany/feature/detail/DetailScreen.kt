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
fun DetailScreen(homeViewModel: HomeViewModel, id: String, route: String) {
    val homeUiState = homeViewModel.uiState.collectAsState().value

    LaunchedEffect(id) {
        if (homeUiState.movieVideoKey != id) {
            homeViewModel.setLoading(true) // 로딩 시작
            homeViewModel.requestMovieVideo(id.toInt())
            homeViewModel.requestMovieDetail(id.toInt())
            homeViewModel.requestMovieCertification(id.toInt())
            homeViewModel.requestMovieCredits(id.toInt())
            homeViewModel.requestTvDetail(id.toInt())
            homeViewModel.requestTvCredits(id.toInt())
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
                        movieId = id,
                        detailUIModel = detailMovieUIModel
                    )
                } else {
                    DetailTvContent(
                        paddingValues = paddingValues,
                        homeUiState = homeUiState,
                        tvId = id,
                        detailUIModel = detailTvUIModel
                    )
                }
            }
        },
        bottomBar = {}
    )
}
