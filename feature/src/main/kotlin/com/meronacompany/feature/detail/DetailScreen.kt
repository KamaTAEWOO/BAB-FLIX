package com.meronacompany.feature.detail

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen

@Composable
fun DetailScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {},
        content = { paddingValues ->
            DetailContent(paddingValues)
        },
        bottomBar = { BottomNavigationScreen(navHostController) }
    )
}

@Composable
fun DetailContent(paddingValues: PaddingValues) {
    // Detail Content
    Text(text = "Detail Screen")
}