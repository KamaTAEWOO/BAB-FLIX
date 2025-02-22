package com.meronacompany.feature.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meronacompany.feature.home.HomeScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController()
) {

    NavHost(
        navController = navHostController,
        startDestination = NavRouteLabel.HOME,
        modifier = Modifier.fillMaxSize(),
        route = NavRouteLabel.MAIN
    ) {
        // Splash
        composable(route = NavRouteLabel.SPLASH) {
            // Splash
        }

        // Home
        composable(route = NavRouteLabel.HOME) {
            HomeScreen()
        }

        // Detail
    }

}
