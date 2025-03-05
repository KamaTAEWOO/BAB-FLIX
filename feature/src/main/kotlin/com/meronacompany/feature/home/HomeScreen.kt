package com.meronacompany.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Home Screen", modifier = Modifier.padding(16.dp))

        // 예제: 더미 아이템 추가 (스크롤 확인용)
        repeat(50) { index ->
            Text(text = "Item #$index", modifier = Modifier.padding(16.dp))
        }
    }
}