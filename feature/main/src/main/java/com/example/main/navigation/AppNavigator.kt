package com.example.main.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.search.SearchScreen

internal class AppNavigator(
    private val navController: NavHostController
) {
    @Composable
    fun AppNavigation(
        paddingValues: PaddingValues
    ) {
        NavHost(navController = navController, startDestination = ScreenRoute.SEARCH.route) {
            composable(
                route = ScreenRoute.SEARCH.route
            ) {
                SearchScreen(paddingValues)
            }

            composable(
                route = ScreenRoute.BOOKMARK.route
            ) {

            }
        }
    }

    fun bottomNavigator(tab: BottomTab) {
        val navOptions = navOptions {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }

        when (tab) {
            BottomTab.SearchTab -> navController.navigate(ScreenRoute.SEARCH.route,navOptions)
            BottomTab.BookmarkTab -> navController.navigate(ScreenRoute.BOOKMARK.route,navOptions)
        }
    }

    val currentRoute = navController.currentBackStackEntry?.destination?.route
}
