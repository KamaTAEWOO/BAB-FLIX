package com.meronacompany.feature.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.meronacompany.design.common.CommonAppBar
import com.meronacompany.feature.movie.MovieContent
import com.meronacompany.feature.navigation.NavRouteLabel
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen
import com.meronacompany.feature.tv.TvContent
import com.meronacompany.design.R
import com.meronacompany.design.theme.BAB_FLIXTheme

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

    val moviePagerIndex by homeViewModel.moviePagerIndex.collectAsState()
    val tvPagerIndex by homeViewModel.tvPagerIndex.collectAsState()

    val currentPage = remember(route) {
        derivedStateOf {
            if (route == NavRouteLabel.MOVIE) {
                moviePagerIndex + 1
            } else {
                tvPagerIndex + 1
            }
        }
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
            PageFloatingButton(currentPage.value, paddingValues)
        },
        bottomBar = { BottomNavigationScreen(navHostController, homeViewModel) }
    )
}

/*
* 추후 다른 추가를 위해 FloatingActionButton을 사용
* */
@Composable
fun PageFloatingButton(currentPage: Int, paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(end = 15.dp, bottom = 15.dp)
    ) {
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = { /* 클릭 동작 없으면 비워둬도 됨 */ },
            containerColor = Color.DarkGray,
            contentColor = Color.White
        ) {
            Text(
                text = currentPage.toString(),
                style = BAB_FLIXTheme.typography.textStyleBold30,
                color = Color.White
            )
        }
    }
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