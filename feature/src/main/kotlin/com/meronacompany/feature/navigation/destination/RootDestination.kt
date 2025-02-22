package com.meronacompany.feature.navigation.destination

import com.meronacompany.feature.navigation.NavRouteLabel

sealed class RootDestination : Destination {
//    object Splash : RootDestination() {
//        override val route = NavRouteLabel.SPLASH
//    }

    object Home : RootDestination() {
        override val route = NavRouteLabel.HOME
    }
}
