package com.meronacompany.feature.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.meronacompany.core.local.PreferenceManager
import com.meronacompany.feature.auth.AuthScreen
import com.meronacompany.feature.detail.DetailScreen
import com.meronacompany.feature.home.HomeScreen
import com.meronacompany.feature.home.HomeViewModel
import com.meronacompany.feature.settings.LanguageScreen
import com.meronacompany.feature.settings.SettingsScreen
import com.meronacompany.feature.settings.VersionScreen
import com.meronacompany.feature.splash.SplashScreen

@Composable
fun AppNavHost(
    navHostController: NavHostController = rememberNavController()
) {
    val context = LocalContext.current
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.provideFactory(context))
    homeViewModel.getLanguage() // 처음을 위해서 사용. 비어있다면, 디바이스 언어 설정을 가져와서 적용해줌.
    // 언어에 따른 update
    PreferenceManager.getLanguage(context).let { homeViewModel.updateLocaleResources(context, it) }

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
            SettingsScreen(
                navHostController,
                homeViewModel,
                onNavigateToLanguage = {
                    navHostController.navigate(NavRouteLabel.LANGUAGE) {
                        popUpTo(NavRouteLabel.SETTINGS) {
                            inclusive = true
                        }
                    }
                },
                onNavigateToVersion = {
                    navHostController.navigate(NavRouteLabel.VERSION) {
                        popUpTo(NavRouteLabel.SETTINGS) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        // Language
        composable(route = NavRouteLabel.LANGUAGE) {
            LanguageScreen(
                homeViewModel,
                onNavigateBack = {
                    navHostController.popBackStack()
                },
                onSelfLanguage = {
                    navHostController.navigate(NavRouteLabel.LANGUAGE) {
                        popUpTo(NavRouteLabel.LANGUAGE) {
                            inclusive = true
                        }
                    }
                })
        }

        // Version
        composable(route = NavRouteLabel.VERSION) {
            VersionScreen(
                homeViewModel,
                onNavigateBack = {
                    navHostController.popBackStack()
                })
        }

    }

}
