package com.meronacompany.feature.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.meronacompany.design.R
import com.meronacompany.design.common.CommonAppBar
import com.meronacompany.feature.movie.MovieContent
import com.meronacompany.feature.navigation.NavRouteLabel
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen
import com.meronacompany.feature.tv.TvContent
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun HomeScreen(
    route: String,
    homeViewModel: HomeViewModel,
    navHostController: NavHostController,
    onNavigateToDetail: (Int, String) -> Unit
) {
    val moviePagerIndex by homeViewModel.moviePagerIndex.collectAsState()
    val tvPagerIndex by homeViewModel.tvPagerIndex.collectAsState()
    Timber.d("HomeScreen: moviePagerIndex = $moviePagerIndex, tvPagerIndex = $tvPagerIndex")

    val isLoading by homeViewModel.isLoading.collectAsState()
    var showContent by remember { mutableStateOf(false) }
    val homeUiState by homeViewModel.uiState.collectAsState()

    // 최초 진입 시 데이터 요청
    LaunchedEffect(Unit) {
        if (homeUiState.allPopularMoviesData.isEmpty()) {
            Timber.d("HomeScreen LaunchedEffect: Initial data request")
            homeViewModel.requestPopularMovies(1)
        }
    }

    // 로딩 종료 후 콘텐츠 표시
    LaunchedEffect(isLoading, homeUiState.allPopularMoviesData) {
        if (!isLoading && homeUiState.allPopularMoviesData.isNotEmpty()) {
            showContent = true
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        topBar = { CommonAppBar() },
        content = { paddingValues ->
            HomeScreenContent(
                showContent = showContent,
                route = route,
                homeViewModel = homeViewModel,
                paddingValues = paddingValues,
                onNavigateToDetail = onNavigateToDetail
            )
             PageFloatingButton(paddingValues, homeViewModel, route)
        },
        bottomBar = { BottomNavigationScreen(navHostController, homeViewModel) }
    )
}

@Composable
fun PageFloatingButton(
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    route: String
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(end = 15.dp, bottom = 15.dp)
    ) {
        FloatingActionButton(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                // 위로 스크롤 이동
                coroutineScope.launch {
                    val scrollStates = if (route == NavRouteLabel.MOVIE) {
                        homeViewModel.movieScrollStates
                    } else {
                        homeViewModel.tvScrollStates
                    }
                    scrollStates.values.forEach { it.animateScrollToItem(0) }
                }
            },
            containerColor = Color.DarkGray,
            contentColor = Color.White
        ) {
            Icon(
                modifier = Modifier.padding(bottom = 5.dp),
                painter = painterResource(id = R.drawable.ic_up_arrow),
                contentDescription = "Up Arrow"
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    showContent: Boolean,
    route: String,
    homeViewModel: HomeViewModel,
    paddingValues: PaddingValues,
    onNavigateToDetail: (Int, String) -> Unit
) {
    Timber.d("HomeScreen: showContent = $showContent")
    if (showContent) {
        if (route == NavRouteLabel.MOVIE) {
            MovieContent(homeViewModel, paddingValues, onNavigateToDetail, route)
        } else {
            TvContent(homeViewModel, paddingValues, onNavigateToDetail, route)
        }
    } else {
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            val isApiLimitExceeded = homeViewModel.apiUsageCount >= homeViewModel.apiLimit
            Text(
                text = if (isApiLimitExceeded)
                    androidx.compose.ui.res.stringResource(id = R.string.api_limit_exceeded)
                else
                    androidx.compose.ui.res.stringResource(id = R.string.loading_data),
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                color = colorScheme.onPrimary
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
        Canvas(modifier = Modifier
            .align(Alignment.Center)
            .then(modifier)) {
            val size = size.minDimension
            val startOffset1 = Offset(0f, 0f)
            val endOffset1 = Offset(size, size)
            val startOffset2 = Offset(size, 0f)
            val endOffset2 = Offset(0f, size)

            drawLine(
                color = color,
                start = startOffset1,
                end = endOffset1,
                strokeWidth = strokeWidth
            )
            drawLine(
                color = color,
                start = startOffset2,
                end = endOffset2,
                strokeWidth = strokeWidth
            )
        }
    }
}