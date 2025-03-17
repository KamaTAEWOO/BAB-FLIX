package com.meronacompany.feature.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.meronacompany.design.common.CommonAppBar
import com.meronacompany.feature.movie.MovieContent
import com.meronacompany.feature.navigation.NavRouteLabel
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen
import com.meronacompany.feature.tv.TvContent
import com.meronacompany.design.R

@Composable
fun HomeScreen(
    route: String,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    onNavigateToDetail: (Int, String) -> Unit
) {
    LaunchedEffect("Unit") {
//        homeViewModel.requestWatchProviders() // ott
//        homeViewModel.requestMovieGenres() // movie 장르
//        homeViewModel.requestTVGenres() // tv 장르
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        topBar = { CommonAppBar(stringResource(R.string.toolbar_name)) },
        content = { paddingValues ->
            if (route == NavRouteLabel.MOVIE) {
                MovieContent(homeViewModel, paddingValues, onNavigateToDetail, route)
            } else {
                TvContent(homeViewModel, paddingValues, onNavigateToDetail, route)
            }
        },
        bottomBar = { BottomNavigationScreen(navHostController) }
    )
}

// image 데이터 없을 때 x 표시
@Composable
fun ImageError(
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    strokeWidth: Float = 8f
) {
    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.align(Alignment.Center).then(modifier)) {
            val size = size.minDimension
            val startOffset1 = Offset(0f, 0f)
            val endOffset1 = Offset(size, size)
            val startOffset2 = Offset(size, 0f)
            val endOffset2 = Offset(0f, size)

            drawLine(color = color, start = startOffset1, end = endOffset1, strokeWidth = strokeWidth)
            drawLine(color = color, start = startOffset2, end = endOffset2, strokeWidth = strokeWidth)
        }
    }
}