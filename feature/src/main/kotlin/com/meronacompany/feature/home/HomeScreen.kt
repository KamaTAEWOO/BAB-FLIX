package com.meronacompany.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.meronacompany.core.utility.Util
import com.meronacompany.design.common.GlideUI
import com.meronacompany.feature.home.model.MovieItem
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory())

    LaunchedEffect("Unit") {
//        homeViewModel.requestIsApiKey()
//        homeViewModel.requestPopularTVs() // popular tv
//        homeViewModel.requestWatchProviders() // ott
//        homeViewModel.requestMovieGenres() // movie 장르
//        homeViewModel.requestTVGenres() // tv 장르
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {},
        content = { paddingValues ->
            HomeContent(
                homeViewModel = homeViewModel,
                paddingValues = paddingValues
            )
        },
        bottomBar = { BottomNavigationScreen(navHostController) }
    )
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeContent(homeViewModel: HomeViewModel, paddingValues: PaddingValues) {
    val homeState = homeViewModel.uiState.value
    var pageCount by remember { mutableIntStateOf(2) } // 초기 페이지 수
    val pagerState = rememberPagerState(pageCount = { pageCount })
    // 만약 allPopularMoviesData에 key가 없다면, requestPopularMovies() 호출
    if (!homeState.allPopularMoviesData.containsKey(pageCount - 1)) {
        homeViewModel.requestPopularMovies()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        HorizontalPager(state = pagerState) { page ->
            // 마지막 페이지에 도달하면 페이지 추가
            if (page == pageCount - 1) {
                pageCount++
            }
            HomeContentListData(
                pageNumber = page + 1,
                homeState = homeState
            )
        }
    }
}

@Composable
fun HomeContentListData(pageNumber: Int, homeState: HomeState?) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(text = "Home Screen - Page $pageNumber")
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(homeState?.popularMovies?.results?.chunked(2) ?: emptyList()) { moviePair ->
            Row(modifier = Modifier.fillMaxWidth()) {
                moviePair.forEach { movie ->
                    val movieItem = MovieItem(
                        id = movie.id,
                        genreIds = movie.genre_ids,
                        title = movie.title,
                        voteAverage = movie.vote_average,
                        posterPath = movie.poster_path ?: ""
                    )
                    MovieData(
                        movieItem = movieItem,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
fun MovieData(movieItem: MovieItem, modifier: Modifier) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        MoviePoster(posterPath = movieItem.posterPath)
        MovieNameAndScore(movieItem = movieItem)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun MoviePoster(posterPath: String) {
    GlideUI.CommonGlideImage(path = posterPath)
}

@Composable
fun MovieNameAndScore(movieItem: MovieItem) {
    Column(
        modifier = Modifier.padding(horizontal = 30.dp)
    ) {
        Text(text = movieItem.title)
        Text(text = Util.formatVoteAverage(movieItem.voteAverage))
    }
}