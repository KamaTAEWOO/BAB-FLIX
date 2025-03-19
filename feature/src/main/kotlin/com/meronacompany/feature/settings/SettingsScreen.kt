package com.meronacompany.feature.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    ) {
        // 앱 정보
        Text(
            text = "Ver 1.0.0",
            color = colorScheme.onPrimary,
            style = BAB_FLIXTheme.typography.textStyleBold18
        )
    }

}