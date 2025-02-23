package com.meronacompany.feature.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.meronacompany.data.repository.HomeRepositoryImpl
import com.meronacompany.feature.di.ViewModelFactory

@Composable
fun HomeScreen() {
    // HomeRepositoryImpl을 직접 생성 (혹은 의존성 주입)
    val homeRepositoryImpl = remember { HomeRepositoryImpl() }

    // ViewModelFactory를 통해 ViewModel을 생성
    val homeViewModel: HomeViewModel = viewModel(
        factory = ViewModelFactory(
            HomeViewModel::class.java,
            homeRepositoryImpl
        ) { HomeViewModel(it) }
    )

    LaunchedEffect("Unit") {
        homeViewModel.requestIsApiKey()
    }

    Scaffold(
        topBar = {},
        content = { paddingValues ->
            HomeContent(paddingValues)
        },
        bottomBar = {}
    )
}

@Composable
fun HomeContent(paddingValues: PaddingValues) {
    // Home Content
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        Text(text = "Home Screen")
    }
}