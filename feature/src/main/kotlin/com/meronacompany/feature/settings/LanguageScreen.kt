package com.meronacompany.feature.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.meronacompany.design.common.CommonAppBarWithBackIcon
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.design.R

/**
 * 오픈 소스 라이브러리
 * About
 * 앱 종료
 */

@Composable
fun LanguageScreen(
    homeViewModel: HomeViewModel,
    onNavigateBack: () -> Unit,
    onSelfLanguage: () -> Unit
) {
    Scaffold(
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        topBar = { CommonAppBarWithBackIcon(title = stringResource(id = R.string.language_setting), onNavigateBack) },
        content = { paddingValues ->
            LanguageContent(paddingValues, homeViewModel, onSelfLanguage)
        },
        bottomBar = { })
}

@Composable
fun LanguageContent(
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    onSelfLanguage: () -> Unit
) {
    val context = LocalContext.current

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
            LanguageRow(title = stringResource(id = R.string.language_ko)) {
                homeViewModel.setLanguage("ko-KR")
                android.widget.Toast.makeText(
                    context,
                    context.getString(R.string.language_changed, context.getString(R.string.language_ko)),
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                onSelfLanguage()
            }

            LanguageRow(title = stringResource(id = R.string.language_en)) {
                homeViewModel.setLanguage("en-US")
                android.widget.Toast.makeText(
                    context,
                    context.getString(R.string.language_changed, context.getString(R.string.language_en)),
                    android.widget.Toast.LENGTH_SHORT
                ).show()
                onSelfLanguage()
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
            painter = painterResource(id = R.drawable.ic_arrow_right),
            contentDescription = "Chevron Right",
            modifier = Modifier.size(35.dp)
        )
    }
}