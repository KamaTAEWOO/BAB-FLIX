package com.meronacompany.feature.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meronacompany.feature.auth.AuthScreen
import com.meronacompany.feature.detail.DetailScreen
import com.meronacompany.feature.home.HomeScreen
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.settings.SettingsScreen
import com.meronacompany.feature.splash.SplashScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory())

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
                    navHostController.navigate(NavRouteLabel.MOVIE) {
                        popUpTo(NavRouteLabel.SPLASH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Auth
        composable(route = NavRouteLabel.AUTH) {
            AuthScreen(
                onNavigateToHome = {
                    navHostController.navigate(NavRouteLabel.MOVIE) {
                        popUpTo(NavRouteLabel.AUTH) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Movie
        composable(route = NavRouteLabel.MOVIE) {
            HomeScreen(
                route = NavRouteLabel.MOVIE,
                homeViewModel,
                navHostController,
                onNavigateToDetail = { movieId, route ->
                    navHostController.navigate("${NavRouteLabel.DETAIL}/$movieId/$route")
                }
            )
        }

        // TV
        composable(route = NavRouteLabel.TV) {
            HomeScreen(
                route = NavRouteLabel.TV,
                homeViewModel,
                navHostController,
                onNavigateToDetail = { movieId, route ->
                    navHostController.navigate("${NavRouteLabel.DETAIL}/$movieId/$route")
                }
            )
        }

        // Detail
        composable(route = "${NavRouteLabel.DETAIL}/{movieId}/{route}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("movieId") ?: ""
            val route = backStackEntry.arguments?.getString("route") ?: ""
            DetailScreen(
                homeViewModel = homeViewModel,
                id = id,
                route = route
            )
        }

        // Settings
        composable(route = NavRouteLabel.SETTINGS) {
            SettingsScreen(navHostController, homeViewModel)
        }
    }

}
