package com.meronacompany.feature.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meronacompany.feature.detail.DetailScreen
import com.meronacompany.feature.home.HomeScreen
import com.meronacompany.feature.settings.SettingsScreen
import com.meronacompany.feature.splash.SplashScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navHostController,
        startDestination = NavRouteLabel.SPLASH,
        modifier = Modifier.fillMaxSize(),
        route = NavRouteLabel.MAIN
    ) {
        // Splash
        composable(route = NavRouteLabel.SPLASH) {
            SplashScreen(
                onNavigateToHome = {
                    navHostController.navigate(NavRouteLabel.HOME) {
                        popUpTo(NavRouteLabel.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Home
        composable(route = NavRouteLabel.HOME) {
            HomeScreen(
                navHostController,
                onNavigateToDetail = { movieId ->
                    navHostController.navigate("${NavRouteLabel.DETAIL}/$movieId")
                }
            )
        }

        // Detail
        composable(route = "${NavRouteLabel.DETAIL}/{movieId}") { backStackEntry ->
            val movieId = backStackEntry.arguments?.getString("movieId") ?: ""
            DetailScreen(movieId = movieId)
        }

        // Settings
        composable(route = NavRouteLabel.SETTINGS) {
            SettingsScreen(navHostController)
        }
    }

}
