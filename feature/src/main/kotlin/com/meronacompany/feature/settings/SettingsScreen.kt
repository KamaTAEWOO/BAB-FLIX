package com.meronacompany.feature.settings

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
            .padding(paddingValues)
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.wrapContentSize(),
            verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
        ) {
            Text(
                text = "오픈 소스 라이브러리",
                color = colorScheme.onPrimary,
                style = BAB_FLIXTheme.typography.textStyleBold18,
                modifier = Modifier.clickable {
                    val intent = Intent(context, OssLicensesMenuActivity::class.java).apply {
                        putExtra("title", "오픈 소스 라이선스")
                    }
                    context.startActivity(intent)
                }
            )

            Spacer(modifier = Modifier.weight(1f))

            Image(
                imageVector = ImageVector.vectorResource(id = com.meronacompany.design.R.drawable.ic_arrow_right),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 앱 정보
        Text(
            text = "Ver 1.0.0",
            color = colorScheme.onPrimary,
            style = BAB_FLIXTheme.typography.textStyleBold18
        )
    }
}