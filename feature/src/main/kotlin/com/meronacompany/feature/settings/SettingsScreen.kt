package com.meronacompany.feature.settings

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    Scaffold(
        topBar = {},
        content = { paddingValues ->
            SettingsContent(paddingValues)
        },
        bottomBar = { BottomNavigationScreen(navHostController) }
    )
}

@Composable
fun SettingsContent(paddingValues: PaddingValues) {
    // Detail Content
    Text(text = "Settings Screen")
}