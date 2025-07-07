package com.meronacompany.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.meronacompany.design.common.CommonAppBar
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.R
import com.meronacompany.feature.home.HomeViewModel

/**
 * 오픈 소스 라이브러리
 * About
 * 앱 종료
 */

@Composable
fun VersionScreen(homeViewModel: HomeViewModel) {
    Scaffold(
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        topBar = { CommonAppBar() },
        content = { paddingValues ->
            VersionContent(paddingValues, homeViewModel)
        },
        bottomBar = { })
}

@Composable
fun VersionContent(paddingValues: PaddingValues, homeViewModel: HomeViewModel) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "버전 " + context.resources.getString(R.string.APP_VERSION) + "." + context.resources.getString(
                    R.string.APP_VERSION_CODE
                ),
                color = Color.White,
                style = BAB_FLIXTheme.typography.textStyleLight18,
            )

            Spacer(modifier = Modifier.height(4.dp))

            val remaining = (homeViewModel.apiLimit - homeViewModel.apiUsageCount).coerceAtLeast(0)
            Text(
                text = "API 잔여 횟수: ${remaining}회 (총 ${homeViewModel.apiLimit}회)",
                color = Color.White,
                style = BAB_FLIXTheme.typography.textStyleLight18,
            )
        }
    }
}
