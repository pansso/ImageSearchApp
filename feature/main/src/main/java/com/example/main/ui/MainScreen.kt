package com.example.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.main.navigation.AppNavigator
import com.example.main.navigation.BottomNavigation
import com.example.main.navigation.BottomTab

@Composable
fun MainScreen(
    navigator: NavHostController
) {
    val selectedTab = navigator.currentBackStackEntryAsState().value?.destination?.route
    val appNavigator = AppNavigator(navigator)

    val currentTab = BottomTab.bottomTabItems.find { it.route == selectedTab }
    val currentTabTitle = currentTab?.let { stringResource(id = it.id) } ?: "title"

    Scaffold(
        topBar = {
                 MainTopBar(title = currentTabTitle )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.fillMaxSize()) {
                appNavigator.AppNavigation(paddingValues = paddingValues)
            }
        },
        bottomBar = {
            BottomNavigation(navigator = appNavigator)
        }
    )
}
