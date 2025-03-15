package com.meronacompany.feature.detail

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.navigation.NavRouteLabel
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailScreen(homeViewModel: HomeViewModel, id: String, route: String) {
    val homeUiState = homeViewModel.uiState.collectAsState().value

    LaunchedEffect(id) {
        if (homeViewModel.movieVideoKey != id) {
            homeViewModel.setLoading(true) // 로딩 시작

            // 모든 요청을 하나의 launch 블록에서 실행
            homeViewModel.movieVideoKey = id // 중복 실행 방지

            if (route == NavRouteLabel.MOVIE) {
                homeViewModel.run {
                    listOf(
                        async { homeViewModel.requestMovieVideo(id.toInt()) },
                        async { requestMovieDetail(id.toInt()) },
                        async { requestMovieCertification(id.toInt()) },
                        async { requestMovieCredits(id.toInt()) },
                    ).awaitAll()
                }
            } else {
                homeViewModel.run {
                    listOf(
                        async { homeViewModel.requestTvVideo(id.toInt()) },
                        async { requestTvDetail(id.toInt()) },
                        async { requestTvCredits(id.toInt()) },
                    ).awaitAll()
                }
            }


            homeViewModel.setLoading(false) // 모든 요청 완료 후 로딩 종료
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
                        homeViewModel = homeViewModel,
                        movieId = id,
                        detailUIModel = detailMovieUIModel
                    )
                } else {
                    DetailTvContent(
                        paddingValues = paddingValues,
                        homeViewModel = homeViewModel,
                        tvId = id,
                        detailUIModel = detailTvUIModel
                    )
                }
            }
        },
        bottomBar = {}
    )
}
