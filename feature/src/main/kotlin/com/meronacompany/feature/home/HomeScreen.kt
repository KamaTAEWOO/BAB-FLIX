package com.meronacompany.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
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
        topBar = {},
        content = { paddingValues ->
            HomeContent(paddingValues)
        },
        bottomBar = { BottomNavigationScreen(navHostController) }
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
    // Home Content
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Text(text = "Home Screen")
    }
}