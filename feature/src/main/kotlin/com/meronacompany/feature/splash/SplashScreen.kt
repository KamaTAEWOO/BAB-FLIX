package com.meronacompany.feature.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(2000)
        onNavigateToHome()
    }

    Scaffold(
        topBar = {},
        content = { SplashContent(it) },
        bottomBar = {}
    )
}

@Composable
fun SplashContent(paddingValues: PaddingValues) {
    var startAnimation by remember { mutableStateOf(false) }

    val animatedScale by animateFloatAsState(
        targetValue = if (startAnimation) 2f else 1f,
        animationSpec = tween(durationMillis = 2000),
        label = "scale-up"
    )

    // 애니메이션 시작 트리거
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = com.meronacompany.design.R.drawable.ic_new_logo),
            contentDescription = "App Logo",
            modifier = Modifier
                .size(150.dp)
                .graphicsLayer {
                    scaleX = animatedScale
                    scaleY = animatedScale
                }
        )
    }
}