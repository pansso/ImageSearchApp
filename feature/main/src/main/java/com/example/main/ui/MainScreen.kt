package com.example.main.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.common.ui.SearchField
import com.example.main.navigation.AppNavigator
import com.example.main.navigation.BottomNavigation
import com.example.main.navigation.BottomTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navigator: NavHostController,
    viewModel: MainViewModel = hiltViewModel()
) {
    val selectedTab = navigator.currentBackStackEntryAsState().value?.destination?.route
    val appNavigator = AppNavigator(navigator)

    val currentTab = BottomTab.bottomTabItems.find { it.route == selectedTab }
    val currentTabTitle = currentTab?.let { stringResource(id = it.id) } ?: "title"
    val searchText by viewModel.searchText.collectAsState()

    Scaffold(
        topBar = {
            Column(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                Text(text = currentTabTitle)
                SearchField(
                    searchText = searchText,
                    onSearchTextChanged = { viewModel.updateSearchText(it) },
                    onTextClear = { viewModel.clearSearchText() })
            }
        },
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
            ) {
                appNavigator.AppNavigation(viewModel.searchText)
            }
        },
        bottomBar = {
            BottomNavigation(navigator = appNavigator)
        }
    )
}
