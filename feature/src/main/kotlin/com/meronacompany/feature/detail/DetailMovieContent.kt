package com.meronacompany.feature.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meronacompany.core.utility.Util.formatDuration
import com.meronacompany.design.common.YoutubePlayer
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.feature.home.HomeState
import timber.log.Timber

@Composable
fun DetailMovieContent(
    paddingValues: PaddingValues,
    homeUiState: HomeState,
    movieId: String?,
    detailUIModel: DetailMovieModel?
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
                "재생" to (detailUIModel?.duration?.toInt()?.let { formatDuration(it) } ?: "No duration"),
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
private fun RowText(title: String, content: String) {
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

fun detailMovieContentData(homeUiState: HomeState): DetailMovieModel? {
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
        val originalLanguage = detail.spokenLanguages[0].name
        val ratingScore = detail.voteAverage.toString()

        detail.runtime.toString().let {
            DetailMovieModel(
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

private fun getCertification(response: ResponseMovieCertificationData, countryCode: String = "KR"): String {
    return response.results
        .firstOrNull { it.iso_3166_1 == countryCode }
        ?.release_dates
        ?.firstOrNull()
        ?.certification
        ?: "등급 정보 없음"
}