package com.meronacompany.feature.splash

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier

@Composable
fun SplashScreen(onNavigateToHome: () -> Unit) {

    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(3000)
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
    Column {
        Text(
            "Splash Screen", modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        )
    }
}