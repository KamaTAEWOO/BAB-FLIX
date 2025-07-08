package com.meronacompany.feature.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.meronacompany.design.R
import com.meronacompany.design.common.CommonAppBarWithBackIcon
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.home.HomeViewModel

/**
 * 오픈 소스 라이브러리
 * About
 * 앱 종료
 */

@Composable
fun VersionScreen(homeViewModel: HomeViewModel, onNavigateBack: () -> Unit) {
    Scaffold(
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        topBar = { CommonAppBarWithBackIcon(stringResource(R.string.version), onNavigateBack) },
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
                text = "${stringResource(R.string.version)} " + context.resources.getString(R.string.APP_VERSION) + "." + context.resources.getString(
                    R.string.APP_VERSION_CODE
                ),
                color = Color.White,
                style = BAB_FLIXTheme.typography.textStyleBold20,
            )

            Spacer(modifier = Modifier.height(4.dp))

            val text = context.getString(
                R.string.api_remaining_count,
                homeViewModel.apiLimit - homeViewModel.apiUsageCount,
                homeViewModel.apiLimit
            )
            Text(
                text = text,
                color = Color.White,
                style = BAB_FLIXTheme.typography.textStyleBold20,
            )
        }
    }
}
