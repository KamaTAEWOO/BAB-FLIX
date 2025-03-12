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
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.feature.home.HomeState
import com.meronacompany.feature.home.HomeViewModel
import timber.log.Timber

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailScreen(homeViewModel: HomeViewModel, movieId: String) {
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
                val detailUIModel = detailContentData(homeUiState)

                DetailContent(
                    paddingValues = paddingValues,
                    homeUiState = homeUiState,
                    movieId = movieId,
                    detailUIModel = detailUIModel
                )
            }
        },
        bottomBar = {}
    )
}

@Composable
fun DetailContent(
    paddingValues: PaddingValues,
    homeUiState: HomeState,
    movieId: String?,
    detailUIModel: DetailModel?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        // Show YouTube player only if movieVideoKey is available
        if (homeUiState.movieVideoKey != null && homeUiState.movieVideoKey != movieId) {
            Timber.d("movieVideoKey: ${homeUiState.movieVideoKey}")
            YoutubePlayer(videoId = homeUiState.movieVideoKey)
            Text(text = detailUIModel.toString())
        }
    }
}

fun detailContentData(homeUiState: HomeState): DetailModel? {
    return homeUiState.movieDetail?.let { detail ->
        val castNames = homeUiState.movieCredits?.cast?.joinToString { it.name } ?: ""
        val directorName =
            homeUiState.movieCredits?.crew?.firstOrNull { it.job == "Director" }?.name ?: ""
        val genreNames = detail.genres.joinToString { it.name }
        val rating = getCertification(
            homeUiState.movieCertification ?: ResponseMovieCertificationData(
                0,
                emptyList()
            )
        )
        val originalLanguage = detail.spokenLanguages.firstOrNull()?.name ?: ""
        val ratingScore = detail.voteAverage.toString()

        detail.runtime.toString().let {
            DetailModel(
                title = detail.title,
                director = directorName,
                cast = castNames,
                duration = it,
                genre = genreNames,
                releaseDate = detail.releaseDate,
                rating = rating,
                originalLanguage = originalLanguage,
                ratingScore = ratingScore,
                overview = detail.overview
            )
        }
    }
}

fun getCertification(response: ResponseMovieCertificationData, countryCode: String = "KR"): String {
    return response.results
        .firstOrNull { it.iso_3166_1 == countryCode }
        ?.release_dates
        ?.firstOrNull()
        ?.certification
        ?: "등급 정보 없음"
}