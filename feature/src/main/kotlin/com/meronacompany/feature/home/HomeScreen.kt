package com.meronacompany.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.meronacompany.design.common.CommonAppBar
import com.meronacompany.feature.movie.MovieContent
import com.meronacompany.feature.navigation.NavRouteLabel
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen
import com.meronacompany.feature.tv.TvContent

@Composable
fun HomeScreen(
    route: String,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    onNavigateToDetail: (Int) -> Unit
) {
    LaunchedEffect("Unit") {
//        homeViewModel.requestWatchProviders() // ott
        homeViewModel.requestMovieGenres() // movie 장르
        homeViewModel.requestTVGenres() // tv 장르
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        topBar = { CommonAppBar("BabFlix") },
        content = { paddingValues ->
            if (route == NavRouteLabel.MOVIE) {
                MovieContent(homeViewModel, paddingValues, onNavigateToDetail)
            } else {
                TvContent(homeViewModel, paddingValues, onNavigateToDetail)
            }
        },
        bottomBar = { BottomNavigationScreen(navHostController) }
    )
}
