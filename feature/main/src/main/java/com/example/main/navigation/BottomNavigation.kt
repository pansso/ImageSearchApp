package com.example.main.navigation

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.common.ui.theme.AppColor

@Composable
internal fun BottomNavigation(navigator: AppNavigator) {
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        BottomTab.bottomTabItems.forEach { tab ->
            BottomTabItem(tab = tab, selected = tab.route == navigator.currentRoute) {
                navigator.bottomNavigator(tab)
            }
        }
    }
}

@Composable
private fun RowScope.BottomTabItem(
    tab: BottomTab,
    selected: Boolean,
    onClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .weight(1f)
            .fillMaxHeight()
            .clickable { onClick() },
        Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painterResource(id = tab.resource),
                contentDescription = stringResource(id = tab.id),
                tint = if (selected) {
                    AppColor.selectedColor()
                } else {
                    AppColor.unselectedColor()
                }
            )
            Text(
                text = stringResource(id = tab.id),
                color = if (selected) AppColor.selectedColor() else AppColor.unselectedColor()
            )
        }
    }
}


@Preview
@Composable
private fun Preview() {
    val icons = BottomTab.bottomTabItems
    Row(
        modifier = Modifier
            .navigationBarsPadding()
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        icons.forEach { tab ->
            BottomTabItem(tab = tab, selected = true) {

            }
        }
    }
}