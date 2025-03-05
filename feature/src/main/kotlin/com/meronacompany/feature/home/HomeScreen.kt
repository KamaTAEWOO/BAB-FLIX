package com.meronacompany.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
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
    val pagerState = rememberPagerState(pageCount = { 2 }) // 2개의 페이지

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        HorizontalPager(state = pagerState) { page ->
            when (page) {
                0 -> HomeScreen()
                1 -> ItemListScreen()
            }
        }
    }
}

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(text = "Home Screen")
        Spacer(modifier = Modifier.height(16.dp))
        repeat(20) { index ->
            Text(text = "HomeScreen Item - 1 #$index", modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ItemListScreen() {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(50) { index ->
            Text(text = "ItemListScreen - 2 #$index", modifier = Modifier.padding(16.dp))
        }
    }
}