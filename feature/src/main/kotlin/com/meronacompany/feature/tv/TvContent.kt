package com.meronacompany.feature.tv

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.meronacompany.core.utility.Util
import com.meronacompany.design.common.CommonGlideImage
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.home.HomeState
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.home.ImageError
import com.meronacompany.feature.tv.model.TvItem
import kotlinx.coroutines.delay
import timber.log.Timber

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun tvContent(
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    onNavigateToDetail: (Int, String) -> Unit,
    route: String
): LazyListState {
    val homeState = homeViewModel.uiState.value
    var pageCount by rememberSaveable { mutableIntStateOf(1) } // 초기 페이지 수

    val pagerState = rememberPagerState(
        initialPage = homeViewModel.tvPagerIndex.value,
        pageCount = { pageCount }
    )

    val listStates = homeViewModel.tvScrollStates

    if (!homeState.allPopularTVsData.containsKey(pageCount - 1)) {
        homeViewModel.requestPopularTVs(pageCount - 1)
    }

    LaunchedEffect(pagerState.currentPage) {
        homeViewModel.setTvPagerIndex(pagerState.currentPage)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        HorizontalPager(state = pagerState) { page ->
            if (page == pageCount - 1) {
                pageCount++
            }
            val currentScrollState = listStates.getOrPut(pagerState.currentPage + 1) { LazyListState() }

            HomeContentListData(
                homeState = homeState,
                pageNumber = page + 1,
                paddingValues = paddingValues,
                scrollState = currentScrollState,
                homeViewModel = homeViewModel,
                onTvClick = { tvId ->
                    Timber.d("tvId: $tvId")
                    onNavigateToDetail(tvId, route)
                }
            )
        }
    }

    return listStates.getOrPut(pagerState.currentPage + 1) { LazyListState() }
}

@Composable
fun HomeContentListData(
    homeState: HomeState?,
    pageNumber: Int,
    paddingValues: PaddingValues,
    scrollState: LazyListState,
    homeViewModel: HomeViewModel,
    onTvClick: (Int) -> Unit
) {

    var filteredTVs = homeState?.allPopularTVsData?.get(pageNumber)?.filter { !it.poster_path.isNullOrBlank() } ?: emptyList()

    if (filteredTVs.size % 2 != 0) {
        filteredTVs = filteredTVs.dropLast(1)
    }

    val tvPairs = filteredTVs.chunked(2)

    LazyColumn(
        state = scrollState,
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.primary)
            .padding(paddingValues)
    ) {
        item {
            Spacer(Modifier.height(4.dp))
        }

        if (homeState?.allPopularTVsData?.get(pageNumber).isNullOrEmpty()) {
            item {
                val isApiLimitExceeded = homeViewModel.apiUsageCount >= homeViewModel.apiLimit
                Text(
                    text = if (isApiLimitExceeded) "API 호출 횟수를 초과했습니다." else "데이터를 불러오는 중...",
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    color = colorScheme.onPrimary
                )
            }
        } else {
            items(tvPairs) { tvPair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
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
        TvPoster(posterPath = tvItem.posterPath ?: "", tvItem = tvItem)
        TvNameAndScore(tvItem = tvItem)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun TvPoster(posterPath: String, tvItem: TvItem) {
    if (posterPath.isEmpty()) {
        ImageError()
        return
    }
    CommonGlideImage(path = posterPath, voteAverage = tvItem.voteAverage)
}

@Composable
fun TvNameAndScore(tvItem: TvItem) {
    Column(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Text(text = tvItem.title ?: "", color = colorScheme.onPrimary)
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "rating",
                tint = Color(0xFFFFCA28),
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = Util.formatVoteAverage(tvItem.voteAverage ?: 0.0),
                color = colorScheme.secondary
            )
        }
    }
}
