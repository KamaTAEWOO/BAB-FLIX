package com.meronacompany.feature.navigation.bottom

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.meronacompany.feature.navigation.NavRouteLabel
import com.meronacompany.feature.navigation.bottom.model.BottomNavItem
import com.meronacompany.design.R

@Composable
fun BottomNavigationScreen(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedColor = colorScheme.onPrimary
    val unselectedColor = colorScheme.secondary

    val items = listOf(
        BottomNavItem(
            route = NavRouteLabel.HOME,
            iconResId = R.drawable.ic_launcher,
            labelResId = R.string.movie
        ),
        BottomNavItem(
            route = NavRouteLabel.HOME,
            iconResId = R.drawable.ic_launcher,
            labelResId = R.string.tv
        ),
        BottomNavItem(
            route = NavRouteLabel.SETTINGS,
            iconResId = R.drawable.ic_launcher,
            labelResId = R.string.settings
        ),
    )

    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        NavigationBar(
            containerColor = colorScheme.tertiary,
            contentColor = colorScheme.tertiary,
            modifier = Modifier.fillMaxWidth()
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                NavigationBarItem(
                    icon = {
                        Icon(
                            painter = painterResource(id = item.iconResId),
                            contentDescription = stringResource(id = item.labelResId),
                            tint = if (isSelected) selectedColor else unselectedColor
                        )
                    },
                    label = {
                        Text(
                            stringResource(id = item.labelResId),
                            color = if (isSelected) selectedColor else unselectedColor
                        )
                    },
                    selected = isSelected,
                    onClick = {
                        if (!isSelected) {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
//                    colors = NavigationBarItemDefaults.colors(
//                        indicatorColor = Color.White,
//                        selectedIconColor = selectedColor,
//                        unselectedIconColor = unselectedColor,
//                        selectedTextColor = selectedColor,
//                        unselectedTextColor = unselectedColor
//                    )
                )
            }
        }
    }
}