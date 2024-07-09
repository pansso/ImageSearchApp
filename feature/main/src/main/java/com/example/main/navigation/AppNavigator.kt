package com.example.main.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.example.bookmark.BookmarkScreen
import com.example.search.SearchScreen
import kotlinx.coroutines.flow.StateFlow

internal class AppNavigator(
    private val navController: NavHostController
) {
    @Composable
    fun AppNavigation(searchText: StateFlow<String>) {
        NavHost(navController = navController, startDestination = ScreenRoute.SEARCH.route) {
            composable(
                route = ScreenRoute.SEARCH.route
            ) {
                SearchScreen(searchText)
            }

            composable(
                route = ScreenRoute.BOOKMARK.route
            ) {
                BookmarkScreen(searchText)
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
