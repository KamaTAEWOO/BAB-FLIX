package com.meronacompany.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen

@Composable
fun HomeScreen(navHostController: NavHostController) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory())

    LaunchedEffect("Unit") {
//        homeViewModel.requestIsApiKey()
//        homeViewModel.requestPopularMovies()
//        homeViewModel.requestPopularTVs()
//        homeViewModel.requestWatchProviders()
//        homeViewModel.requestMovieGenres()
//        homeViewModel.requestTVGenres()
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {},
        content = { paddingValues ->
            HomeContent(paddingValues)
        },
        bottomBar = { BottomNavigationScreen(navHostController) }
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
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
            HomeContentListData(page + 1)
        }
    }
}

@Composable
fun HomeContentListData(pageNumber: Int) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "Home Screen - Page $pageNumber")
        Spacer(modifier = Modifier.height(16.dp))
        repeat(20) { index ->
            Text(text = "HomeScreen Item - $pageNumber, #$index", modifier = Modifier.padding(8.dp))
        }
    }
}