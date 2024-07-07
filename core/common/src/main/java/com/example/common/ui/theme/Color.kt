package com.example.common.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColor {
    val Purple80 = Color(0xFFD0BCFF)
    val PurpleGrey80 = Color(0xFFCCC2DC)
    val Pink80 = Color(0xFFEFB8C8)

    val Purple40 = Color(0xFF6650a4)
    val PurpleGrey40 = Color(0xFF625b71)
    val Pink40 = Color(0xFF7D5260)

    val LightBlue200 = Color(0xFF81D4FA)
    val LightBlue700 = Color(0xFF0288D1)
    val BlueGrey200 = Color(0xFFB0BEC5)
    val BlueGrey600 = Color(0xFF546E7A)

    @Composable fun selectedColor() = if (isSystemInDarkTheme()) AppColor.LightBlue200 else AppColor.LightBlue700
    @Composable fun unselectedColor() = if (isSystemInDarkTheme()) AppColor.BlueGrey200 else AppColor.BlueGrey600
}