package com.meronacompany.feature.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meronacompany.design.common.YoutubePlayer
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.feature.R
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
            .verticalScroll(rememberScrollState()) // Enable scrolling
    ) {
        // Show YouTube player only if movieVideoKey is available
        if (homeUiState.movieVideoKey != null && homeUiState.movieVideoKey != movieId) {
            Timber.d("movieVideoKey: ${homeUiState.movieVideoKey}")
            YoutubePlayer(videoId = homeUiState.movieVideoKey)
            // title
            Text(
                modifier = Modifier.padding(16.dp),
                text = detailUIModel?.title ?: "No title",
                style = BAB_FLIXTheme.typography.textStyleBold24
            )

            val details = listOf(
                "감독" to (detailUIModel?.director ?: "No director"),
                "출연" to (detailUIModel?.cast ?: "No cast"),
                "재생" to (detailUIModel?.duration ?: "No duration"),
                "장르" to (detailUIModel?.genre ?: "No genre"),
                "개봉일" to (detailUIModel?.releaseDate ?: "No release date"),
                "시청등급" to ("${detailUIModel?.rating}세 관람가"),
                "원어" to (detailUIModel?.originalLanguage ?: "No original language"),
                "별점" to (detailUIModel?.ratingScore ?: "No rating score")
            )

            details.forEach { (title, content) ->
                RowText(title = title, content = content)
            }

            // overview
            Text(
                modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
                style = BAB_FLIXTheme.typography.textStyleBold18,
                text = "줄거리"
            )

            Text(
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                text = detailUIModel?.overview ?: "No overview",
                style = BAB_FLIXTheme.typography.textStyleLight16,
            )
        }
    }
}

@Composable
fun RowText(title: String, content: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.Top // Ensure title stays at the top
    ) {
        Text(
            text = title,
            style = BAB_FLIXTheme.typography.textStyleBold16,
            modifier = Modifier
                .width(80.dp) // Fixed width for consistency
                .align(Alignment.Top) // Align title to top
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = content,
            style = BAB_FLIXTheme.typography.textStyleLight16,
            modifier = Modifier.weight(1f)
        )
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