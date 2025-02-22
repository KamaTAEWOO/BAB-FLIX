package com.meronacompany.feature.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {},
        content = { paddingValues ->
            HomeContent(paddingValues)
        },
        bottomBar = {}
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
    // Home Content
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        Text(text = "Home Screen")
    }
}