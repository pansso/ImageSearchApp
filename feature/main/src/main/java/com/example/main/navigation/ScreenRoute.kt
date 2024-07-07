package com.example.main.navigation

import java.util.Locale

internal enum class ScreenRoute {
    SEARCH,
    BOOKMARK;

    val route: String
        get() = this.name.lowercase(Locale.ROOT)
}