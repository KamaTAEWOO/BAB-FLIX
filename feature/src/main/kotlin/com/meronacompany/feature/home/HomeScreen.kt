package com.meronacompany.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeScreen() {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory())

    LaunchedEffect("Unit") {
        homeViewModel.requestIsApiKey()
        homeViewModel.requestGetLanguages()
    }

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