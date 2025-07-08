package com.meronacompany.feature.settings

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.meronacompany.design.common.CommonAppBar
import com.meronacompany.design.theme.BAB_FLIXTheme
import com.meronacompany.design.R
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.navigation.bottom.BottomNavigationScreen

/**
 * 오픈 소스 라이브러리
 * About
 * 앱 종료
 */

@Composable
fun SettingsScreen(
    navHostController: NavHostController,
    homeViewModel: HomeViewModel,
    onNavigateToLanguage: () -> Unit,
    onNavigateToVersion: () -> Unit
) {
    Scaffold(
        containerColor = colorScheme.primary,
        contentColor = colorScheme.primary,
        topBar = { CommonAppBar() },
        content = { paddingValues ->
            SettingsContent(paddingValues, homeViewModel, onNavigateToLanguage, onNavigateToVersion)
        },
        bottomBar = { BottomNavigationScreen(navHostController, homeViewModel) })
}

@Composable
fun SettingsContent(
    paddingValues: PaddingValues,
    homeViewModel: HomeViewModel,
    onNavigateToLanguage: () -> Unit,
    onNavigateToVersion: () -> Unit
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
            Spacer(modifier = Modifier.height(16.dp))

            SettingRow(title = stringResource(id = R.string.language)) {
                onNavigateToLanguage()
            }

            SettingRow(title = stringResource(id = R.string.open_source_license)) {
                val intent = Intent(context, OssLicensesMenuActivity::class.java).apply {
                    putExtra("title", context.getString(R.string.open_source_license))
                }
                context.startActivity(intent)
            }

            SettingRow(title = stringResource(id = R.string.version)) {
                onNavigateToVersion()
            }
        }

        // Fixed TMDB Attribution at Bottom
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_tmdb_logo),
                contentDescription = "TMDB Logo",
                modifier = Modifier
                    .size(70.dp)
                    .padding(end = 8.dp)
            )
            Text(
                text = stringResource(id = R.string.tmdb_api_notice),
                style = BAB_FLIXTheme.typography.textStyleBold20,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Visible,
                softWrap = false,
                modifier = Modifier
                    .weight(1f)
                    .basicMarquee()
            )
        }
    }
}

@Composable
fun SettingRow(title: String, onClick: () -> Unit) {
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