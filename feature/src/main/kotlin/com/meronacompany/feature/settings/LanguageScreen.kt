package com.meronacompany.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.meronacompany.design.common.CommonAppBarWithBackIcon
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.home.HomeViewModel

/**
 * 오픈 소스 라이브러리
 * About
 * 앱 종료
 */

@Composable
fun LanguageScreen(homeViewModel: HomeViewModel, onNavigateBack: () -> Unit) {
    Scaffold(
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        topBar = { CommonAppBarWithBackIcon("언어 설정", onNavigateBack) },
        content = { paddingValues ->
            LanguageContent(paddingValues, homeViewModel)
        },
        bottomBar = { })
}

@Composable
fun LanguageContent(paddingValues: PaddingValues, homeViewModel: HomeViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            LanguageRow(title = "한국어") {

            }

            LanguageRow(title = "영어") {

            }
        }
    }
}

@Composable
fun LanguageRow(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = BAB_FLIXTheme.typography.textStyleLight18,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Image(
            painter = painterResource(id = com.meronacompany.design.R.drawable.ic_arrow_right),
            contentDescription = "Chevron Right",
            modifier = Modifier.size(35.dp)
        )
    }
}