package com.meronacompany.feature.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.meronacompany.design.R

@Composable
fun AuthScreen(onNavigateToHome: () -> Unit) {
    Scaffold(
        topBar = {},
        content = { paddingValues ->
            AuthContent(paddingValues, onNavigateToHome)
        },
        bottomBar = {}
    )
}

@Composable
fun AuthContent(paddingValues: PaddingValues, onNavigateToHome: () -> Unit) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues)) {
        Column(
            modifier = Modifier
                .offset(y = (200).dp) // 중앙에서 위로 100dp 이동
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_logo),
                contentDescription = "Logo"
            )
        }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 50.dp)
                .size(50.dp)
                .background(color = Color(0xFFFEE500), shape = CircleShape)
                .clickable(onClick = onNavigateToHome),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_kakao_logo), // 말풍선 아이콘 (검정)
                contentDescription = "Kakao Icon",
                modifier = Modifier.size(30.dp)
            )
        }
    }
}