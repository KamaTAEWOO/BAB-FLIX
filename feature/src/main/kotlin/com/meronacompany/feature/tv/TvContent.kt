package com.meronacompany.feature.tv

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meronacompany.core.utility.Util
import com.meronacompany.design.common.CommonGlideImage
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.home.HomeState
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.tv.model.TvItem
import timber.log.Timber

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TvContent(
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    onNavigateToDetail: (Int, String) -> Unit,
    route: String
) {
    val homeState = homeViewModel.uiState.value
    var pageCount by remember { mutableIntStateOf(2) } // 초기 페이지 수
    val pagerState = rememberPagerState(pageCount = { pageCount })
    // 만약 allPopularMoviesData에 key가 없다면, requestPopularMovies() 호출
    if (!homeState.allPopularTVsData.containsKey(pageCount - 1)) {
        homeViewModel.requestPopularTVs(pageCount - 1)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(state = pagerState) { page ->
            // 마지막 페이지에 도달하면 페이지 추가
            if (page == pageCount - 1) {
                pageCount++
            }
            HomeContentListData(
                homeState = homeState,
                pageNumber = page + 1,
                paddingValues = paddingValues,
                onTvClick = { tvId ->
                    Timber.d("tvId: $tvId")
                    // Detail 화면으로 이동
                    onNavigateToDetail(tvId, route)
                }
            )
        }
    }
}

@Composable
fun HomeContentListData(
    homeState: HomeState?,
    pageNumber: Int,
    paddingValues: PaddingValues,
    onTvClick: (Int) -> Unit
) {
    val tvPairs = homeState?.allPopularTVsData?.get(pageNumber)?.chunked(2)

    if (tvPairs.isNullOrEmpty()) {
        Text(
            text = "데이터를 불러오는 중...",
            modifier = Modifier.padding(16.dp),
            color = colorScheme.onPrimary
        )
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.primary)
            .padding(paddingValues)
    ) {
//        item {
////            Box(
////                modifier = Modifier
////                    .fillMaxSize()
////                    .background(Color.Gray)
////            ) {
////                GenresListData(homeState)
////            }
//        }

        item {
            Spacer(Modifier.height(16.dp))
        }

        items(tvPairs) { tvPair ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                tvPair.forEach { tv ->
                    val tvItem = TvItem(
                        id = tv.id,
                        genreIds = tv.genre_ids,
                        title = tv.name,
                        voteAverage = tv.vote_average,
                        posterPath = tv.poster_path
                    )
                    TvData(
                        tvItem = tvItem,
                        modifier = Modifier.weight(1f),
                        onClick = onTvClick
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun GenresListData(homeState: HomeState) {
    val genres = homeState.genresTVs?.genres ?: emptyList()

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.primary)
            .padding(horizontal = 20.dp),
    ) {
        items(genres) { genre ->
            Box(
                modifier = Modifier
                    .background(colorScheme.primary)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = genre.name,
                    color = colorScheme.onPrimary,
                    style = BAB_FLIXTheme.typography.textStyleBold18
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun TvData(tvItem: TvItem, modifier: Modifier, onClick: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(tvItem.id ?: 0) }
            .then(modifier)
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        TvPoster(posterPath = tvItem.posterPath ?: "")
        TvNameAndScore(tvItem = tvItem)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun TvPoster(posterPath: String) {
    CommonGlideImage(path = posterPath)
}

@Composable
fun TvNameAndScore(tvItem: TvItem) {
    Column(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Text(text = tvItem.title ?: "", color = colorScheme.onPrimary)
        Text(text = Util.formatVoteAverage(tvItem.voteAverage ?: 0.0), color = colorScheme.onPrimary)
    }
}
