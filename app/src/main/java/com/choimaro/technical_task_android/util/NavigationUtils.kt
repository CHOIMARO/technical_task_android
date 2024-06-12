package com.choimaro.technical_task_android.util

import androidx.navigation.NavHostController
import com.choimaro.technical_task_android.ui.Destination
import com.choimaro.technical_task_android.ui.MainNav
import com.choimaro.technical_task_android.ui.NavigationRouteName
import com.choimaro.technical_task_android.ui.ImageDetailNav

object NavigationUtils {
    fun navigate(
        controller: NavHostController,
        routeName: String,
        backStackRouteName: String? = null,
        isLaunchSingleTop: Boolean = true,
        needToRestoreState: Boolean = true
    ) {
        controller.navigate(routeName) {
            if (backStackRouteName != null) {
                popUpTo(backStackRouteName) { saveState = true }
            }
            launchSingleTop = isLaunchSingleTop
            restoreState = needToRestoreState
        }
    }

    fun findDestination(route: String?) : Destination {
        return when(route) {
            NavigationRouteName.SEARCH_SCREEN -> MainNav.Search
            NavigationRouteName.BOOKMARK_SCREEN -> MainNav.Bookmark

            ImageDetailNav.routeWithArgName() -> ImageDetailNav

            else -> MainNav.Search
        }
    }
}