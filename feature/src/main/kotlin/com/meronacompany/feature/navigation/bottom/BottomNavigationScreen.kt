package com.meronacompany.feature.navigation.bottom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.meronacompany.feature.navigation.NavRouteLabel
import com.meronacompany.feature.navigation.bottom.model.BottomNavItem

@Composable
fun BottomNavigationScreen(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val selectedColor = Color.Red
    val unselectedColor = Color.Gray

    val items = listOf(
        BottomNavItem(
            route = NavRouteLabel.HOME,
            iconResId = com.meronacompany.design.R.drawable.ic_launcher,
            labelResId = com.meronacompany.design.R.string.app_name
        ),
        BottomNavItem(
            route = NavRouteLabel.DETAIL,
            iconResId = com.meronacompany.design.R.drawable.ic_launcher,
            labelResId = com.meronacompany.design.R.string.app_name
        ),
        BottomNavItem(
            route = NavRouteLabel.SETTINGS,
            iconResId = com.meronacompany.design.R.drawable.ic_launcher,
            labelResId = com.meronacompany.design.R.string.app_name
        ),
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth(),
        color = Color.Transparent
    ) {
        NavigationBar(
            containerColor = Color.Transparent,
            contentColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp, 20.dp, 0.dp, 0.dp))
                .background(Color.White)
                .shadow(2.dp)
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