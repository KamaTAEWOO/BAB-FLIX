package com.meronacompany.feature.movie

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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.meronacompany.core.utility.Util
import com.meronacompany.design.common.CommonGlideImage
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.home.HomeState
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.movie.model.MovieItem
import timber.log.Timber

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun movieContent(
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    onNavigateToDetail: (Int, String) -> Unit,
    route: String
): LazyListState {
    val homeState = homeViewModel.uiState.value
    var pageCount by rememberSaveable { mutableIntStateOf(1) } // 초기 페이지 수

    val pagerState = rememberPagerState(
        initialPage = homeViewModel.moviePagerIndex.value,
        pageCount = { pageCount }
    )
    // LazyListState 저장용 Map from ViewModel
    val listStates = homeViewModel.movieScrollStates

    if (!homeState.allPopularMoviesData.containsKey(pageCount - 1)) {
        homeViewModel.requestPopularMovies(pageCount - 1)
    }

    LaunchedEffect(pagerState.currentPage) {
        homeViewModel.setMoviePagerIndex(pagerState.currentPage)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(state = pagerState) { page ->
            if (page == pageCount - 1) {
                pageCount++
            }

            val scrollState = listStates.getOrPut(pagerState.currentPage + 1) { LazyListState() }

            HomeContentListData(
                homeState = homeState,
                pageNumber = page + 1,
                paddingValues = paddingValues,
                homeViewModel = homeViewModel,
                onMovieClick = { movieId ->
                    onNavigateToDetail(movieId, route)
                },
                scrollState = scrollState
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
    homeViewModel: HomeViewModel,
    onMovieClick: (Int) -> Unit,
    scrollState: LazyListState
) {
    var filteredMovies = homeState?.allPopularMoviesData?.get(pageNumber)?.filter { !it.poster_path.isNullOrBlank() } ?: emptyList()

    // Remove the last item if the number of movies is odd
    if (filteredMovies.size % 2 != 0) {
        filteredMovies = filteredMovies.dropLast(1)
    }

    val moviePairs = filteredMovies.chunked(2)

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

        if (homeState?.allPopularMoviesData?.get(pageNumber).isNullOrEmpty()) {
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
            items(moviePairs) { moviePair ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = if (moviePair.size == 1) androidx.compose.foundation.layout.Arrangement.Start else androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
                ) {
                    moviePair.forEach { movie ->
                        val movieItem = MovieItem(
                            id = movie.id,
                            genreIds = movie.genre_ids,
                            title = movie.title,
                            voteAverage = movie.vote_average,
                            posterPath = movie.poster_path
                        )

                        MovieData(
                            movieItem = movieItem,
                            modifier = Modifier.weight(1f),
                            onClick = onMovieClick
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun GenresListData(homeState: HomeState) {
    val genres = homeState.genresMovies?.genres ?: emptyList()

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
fun MovieData(movieItem: MovieItem, modifier: Modifier, onClick: (Int) -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(movieItem.id ?: 0) }
            .then(modifier)
    ) {
        Spacer(modifier = Modifier.height(4.dp))

        MoviePoster(posterPath = movieItem.posterPath ?: "")
        MovieNameAndScore(movieItem = movieItem)
        Spacer(modifier = Modifier.height(4.dp))
    }
}

@Composable
fun MoviePoster(posterPath: String) {
    Timber.d("Poster Path: $posterPath") // 1bhIezUxvLM9r66yIf1i6EDVJ6R.jpg
    if (posterPath.isEmpty()) {
        // image placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(colorScheme.onPrimary)
        ) {
            Text(
                text = "No Image",
                color = colorScheme.primary,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(16.dp)
            )
        }
    } else {
         CommonGlideImage(path = posterPath)
    }
}

@Composable
fun MovieNameAndScore(movieItem: MovieItem) {
    Column(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Text(text = movieItem.title ?: "", color = colorScheme.onPrimary)
        Text(
            text = Util.formatVoteAverage(movieItem.voteAverage ?: 0.0),
            color = colorScheme.secondary
        )
    }
}

fun refreshMovieContent(homeViewModel: HomeViewModel, pageCount: Int) {
    for (page in 0 until pageCount) {
        homeViewModel.requestPopularMovies(page)
    }
}