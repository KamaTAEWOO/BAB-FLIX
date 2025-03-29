package com.meronacompany.feature.tv

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meronacompany.core.utility.Util
import com.meronacompany.design.R
import com.meronacompany.design.common.YoutubePlayer
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.tv.model.DetailTvModel
import com.meronacompany.feature.home.HomeState
import com.meronacompany.feature.home.HomeViewModel
import timber.log.Timber

@Composable
fun DetailTvContent(
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    tvId: String?,
    detailUIModel: DetailTvModel?
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        if (homeViewModel.tvVideoKey != tvId) {
            Timber.d("movieVideoKey: ${homeViewModel.tvVideoKey}")
            YoutubePlayer(videoId = homeViewModel.tvVideoKey)

            Text(
                modifier = Modifier.padding(16.dp),
                text = detailUIModel?.title ?: stringResource(R.string.data_error),
                style = BAB_FLIXTheme.typography.textStyleBold24
            )

            val details = listOf(
                stringResource(R.string.creator) to (detailUIModel?.creator ?: stringResource(R.string.data_error)),
                stringResource(R.string.cast) to (detailUIModel?.cast ?: stringResource(R.string.data_error)),
                stringResource(R.string.genre) to (detailUIModel?.genre ?: stringResource(R.string.data_error)),
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
                modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                text = detailUIModel?.overview ?: stringResource(R.string.data_error),
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

fun detailTvContentData(homeUiState: HomeState): DetailTvModel? {
    return homeUiState.tvDetail?.let { detail ->
        val castNames = homeUiState.movieCredits?.cast?.joinToString { it.name } ?: ""
        val directorName =
            homeUiState.movieCredits?.crew?.firstOrNull { it.job == "Director" }?.name ?: ""
        val genreNames = detail.genres.joinToString { it.name }
        val originalLanguage = detail.spoken_languages.firstOrNull()?.name ?: ""
        val ratingScore = detail.vote_average.toString()

        DetailTvModel(
            title = detail.name,
            creator = detail.created_by.firstOrNull()?.name ?: directorName,
            cast = castNames,
            genre = genreNames,
            originalLanguage = originalLanguage,
            ratingScore = ratingScore,
            overview = detail.overview
        )
    }
}
