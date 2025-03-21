package com.meronacompany.feature.settings

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
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
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.TopCenter)
            .padding(paddingValues)
            .padding(top = 100.dp)
    ) {
        // 앱 이름
        Text(
            text = "BAB FLIX",
            color = colorScheme.onPrimary,
            style = BAB_FLIXTheme.typography.textStyleBold30,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(8.dp))
        // 버전
        Text(
            text = "버전 1.0.0",
            color = colorScheme.onPrimary,
            style = BAB_FLIXTheme.typography.textStyleLight18,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(4.dp))
        // 최신 버전입니다.
        Text(
            text = "최신 버전입니다.",
            color = colorScheme.onPrimary,
            style = BAB_FLIXTheme.typography.textStyleLight18,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.weight(1f))  // 빈 공간을 채워 하단으로 밀기

        // 오픈 소스 라이브러리 Button
        Button(
            onClick = {
                val intent = Intent(context, OssLicensesMenuActivity::class.java).apply {
                    putExtra("title", "오픈 소스 라이선스")
                }
                context.startActivity(intent)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 50.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = colorScheme.tertiary,
                contentColor = colorScheme.onPrimary
            )
        ) {
            Text(
                text = "오픈 소스 라이브러리",
                style = BAB_FLIXTheme.typography.textStyleBold18
            )
        }
    }
}