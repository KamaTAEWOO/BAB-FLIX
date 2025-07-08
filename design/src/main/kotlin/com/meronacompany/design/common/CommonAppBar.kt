package com.meronacompany.design.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.meronacompany.design.theme.BAB_FLIXTheme

@Composable
fun CommonAppBar() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .height(60.dp)
            .background(color = colorScheme.primary) // TODO : colorScheme.primary
            .padding(horizontal = 24.dp)
    ) {
        Image(
            painter = painterResource(id = com.meronacompany.design.R.drawable.ic_new_logo),  // Assuming the app icon is saved in the res/drawable folder as ic_new_logo
            contentDescription = "App Icon",
            modifier = Modifier
                .size(100.dp)  // Adjust the size as needed
                .align(Alignment.CenterStart)
        )
    }
}

// arrow back icon AppBar
@Composable
fun CommonAppBarWithBackIcon(
    title: String = "",
    onBackClick: () -> Unit
) {
    BAB_FLIXTheme {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(60.dp)
                .background(color = colorScheme.primary)
                .padding(horizontal = 16.dp)
        ) {
            IconButton(onClick = onBackClick, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            androidx.compose.material3.Text(
                text = title,
                color = Color.White,
                style = BAB_FLIXTheme.typography.textStyleBold20,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}