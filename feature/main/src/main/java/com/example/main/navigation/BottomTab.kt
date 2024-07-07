package com.example.main.navigation

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import javax.annotation.concurrent.Immutable
import com.example.common.R

@Immutable
data class BottomTab(
    @StringRes val id: Int,
    @DrawableRes val resource: Int,
    val route: String
) {
    companion object {
        @Stable
        internal val SearchTab: BottomTab = BottomTab(
            id = R.string.search,
            resource = R.drawable.baseline_search_24,
            route = ScreenRoute.SEARCH.route
        )

        @Stable
        internal val BookmarkTab: BottomTab = BottomTab(
            id = R.string.bookmark,
            resource = R.drawable.baseline_bookmarks_24,
            route = ScreenRoute.BOOKMARK.route
        )

        internal val bottomTabItems = listOf(
            BottomTab.SearchTab,
            BottomTab.BookmarkTab,
        )
    }


}

