package com.meronacompany.feature.movie

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meronacompany.core.utility.Util
import com.meronacompany.core.utility.Util.formatDuration
import com.meronacompany.design.common.YoutubePlayer
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.domain.model.ResponseMovieCertificationData
import com.meronacompany.feature.home.HomeState
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.design.R

@Composable
fun DetailMovieContent(
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    movieId: String?,
    detailUIModel: DetailMovieModel?
) {
    val movieVideoKey = homeViewModel.movieVideoKey

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState()) // Enable scrolling
    ) {
        if (movieVideoKey != movieId) {
            YoutubePlayer(videoId = movieVideoKey)
        } else {
            // video 데이터 없음 처리
            NotYoutubePlayerVideo(movieVideoKey, movieId)
        }

        Text(
            modifier = Modifier.padding(16.dp),
            text = detailUIModel?.title ?: "No title",
            style = BAB_FLIXTheme.typography.textStyleBold24
        )

        val details = listOf(
            stringResource(R.string.director) to (detailUIModel?.director ?: stringResource(R.string.data_error)),
            stringResource(R.string.cast) to (detailUIModel?.cast ?: stringResource(R.string.data_error)),
            stringResource(R.string.duration) to (detailUIModel?.duration?.toInt()?.let { formatDuration(it) } ?: stringResource(R.string.data_error)),
            stringResource(R.string.genre) to (detailUIModel?.genre ?: stringResource(R.string.data_error)),
            stringResource(R.string.release_date) to (detailUIModel?.releaseDate ?: stringResource(R.string.data_error)),
            stringResource(R.string.watch_rating) to (
                    if(detailUIModel?.rating == "All") {
                        "${detailUIModel.rating} 관람가"
                    } else if (detailUIModel?.rating == "등급 정보 없음") {
                        "등급 정보 없음"
                    } else {
                        "${detailUIModel?.rating}세 관람가"
                    }),
            stringResource(R.string.original_language) to (detailUIModel?.originalLanguage ?: stringResource(R.string.data_error)),
            stringResource(R.string.rating) to (detailUIModel?.ratingScore?.toDouble()
                ?.let { Util.formatVoteAverage(it) }
                ?: stringResource(R.string.data_error))
        )

        details.forEach { (title, content) ->
            RowText(title = title, content = content)
        }

        Text(
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp),
            style = BAB_FLIXTheme.typography.textStyleBold18,
            text = stringResource(R.string.store)
        )

        Text(
            modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            text = if(detailUIModel?.overview == "") stringResource(R.string.data_error) else detailUIModel?.overview ?: stringResource(R.string.data_error),
            style = BAB_FLIXTheme.typography.textStyleLight16,
        )
    }
}

@Composable
private fun NotYoutubePlayerVideo(movieVideoKey: String, movieId: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(16f / 9f), // YouTube 비율 유지
        contentAlignment = Alignment.Center
    ) {
        if (movieVideoKey != movieId) {
            YoutubePlayer(videoId = movieVideoKey)
        } else {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val width = size.width
                val height = size.height

                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, 0f),
                    end = Offset(width, height),
                    strokeWidth = 8f
                )
                drawLine(
                    color = Color.Gray,
                    start = Offset(width, 0f),
                    end = Offset(0f, height),
                    strokeWidth = 8f
                )
            }
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
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = title,
            style = BAB_FLIXTheme.typography.textStyleBold16,
            modifier = Modifier
                .width(80.dp)
                .align(Alignment.Top)
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
        val originalLanguage = detail.spokenLanguages.firstOrNull()?.name ?: ""
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