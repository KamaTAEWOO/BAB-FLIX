package com.meronacompany.feature.settings

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen

/**
 * 오픈 소스 라이브러리
 * About
 * 앱 종료
 */

@Composable
fun SettingsScreen(navHostController: NavHostController) {
    Scaffold(topBar = {}, content = { paddingValues ->
        SettingsContent(paddingValues)
    }, bottomBar = { BottomNavigationScreen(navHostController) })
}

@Composable
fun SettingsContent(paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(16.dp) // Added padding for better spacing
    ) {
        // 앱 정보
        Text(
            text = "Ver 1.0.0",
            color = colorScheme.onPrimary,
            style = BAB_FLIXTheme.typography.textStyleBold18
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 오픈 소스 라이브러리
        Text(
            text = "오픈 소스 라이브러리",
            color = colorScheme.primary,
            style = BAB_FLIXTheme.typography.textStyleBold18,
            modifier = Modifier
                .clickable { /* TODO: Navigate to open source screen */ }
                .padding(8.dp)
        )
    }
}