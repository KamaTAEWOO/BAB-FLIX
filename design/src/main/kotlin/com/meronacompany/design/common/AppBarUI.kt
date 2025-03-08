package com.meronacompany.design.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object AppBarUI {

    @Composable
    fun CommonAppBar(
        title: String
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(60.dp)
                .background(color = Color.White) // TODO : colorScheme.primary
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = title,
                color = colorScheme.onPrimary,
                modifier = Modifier.align(Alignment.CenterStart)
            )
        }
    }
}