package com.meronacompany.feature.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.meronacompany.design.common.CommonGlide
import com.meronacompany.feature.home.model.MovieItem
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen
import timber.log.Timber

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun HomeScreen(navHostController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory())
    val homeState = homeViewModel.uiState.value

    LaunchedEffect("Unit") {
//        homeViewModel.requestIsApiKey()
        homeViewModel.requestPopularMovies() // popular movie
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
                paddingValues = paddingValues,
                homeState = homeState
            )
        },
        bottomBar = { BottomNavigationScreen(navHostController) }
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues, homeState: HomeState?) {
    var pageCount by remember { mutableIntStateOf(2) } // 초기 페이지 수
    val pagerState = rememberPagerState(pageCount = { pageCount })

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
        items(homeState?.popularMovies?.results ?: emptyList()) { movie ->
            val movieItem = MovieItem(
                id = movie.id,
                genreIds = movie.genre_ids,
                title = movie.title,
                voteAverage = movie.vote_average,
                posterPath = movie.poster_path ?: ""
            )
            Timber.d("movieItem: $movieItem")
            MovieData(movieItem)
        }
    }
}

@Composable
fun MovieData(movieItem: MovieItem) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "ID: ${movieItem.id}")
        Text(text = "Title: ${movieItem.title}")
        Text(text = "Vote Average: ${Util.formatVoteAverage(movieItem.voteAverage)}") // 소스점 한자리 수 까지만 표시
        Text(text = "Poster Path: ${movieItem.posterPath}")
        Spacer(modifier = Modifier.height(16.dp))
        CommonGlide.GlideImage(path = movieItem.posterPath)
    }
}