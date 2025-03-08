package com.meronacompany.design.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object AppBarUI {

    @Composable
    fun CommonAppBar(
        title: String
    ) {
        // status bar 색상 변경
        val systemUiController = rememberSystemUiController()
        systemUiController.setStatusBarColor(Color.Blue)

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color.White)
                .padding(horizontal = 16.dp)
        ) {
            // 타이틀 중앙 배치
            Text(
                text = title,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}