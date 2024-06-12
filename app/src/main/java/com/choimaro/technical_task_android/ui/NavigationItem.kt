package com.choimaro.technical_task_android.ui

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.choimaro.domain.model.ImageModel
import com.choimaro.technical_task_android.R
import com.choimaro.technical_task_android.util.GsonUtils


/**
 * MainNav를 만들어 MainActivity에서 BottomAppBar가 필요없는 Screen으로 이동 시 안보이게 할 수 있음
 */
sealed class MainNav(override val route: String, override val icon: Int, override val title: String) : Destination {
    object Search : MainNav(NavigationRouteName.SEARCH_SCREEN, R.drawable.ic_search, NavigationTitle.SEARCH_SCREEN)
    object Bookmark : MainNav(NavigationRouteName.BOOKMARK_SCREEN, R.drawable.ic_book_mark, NavigationTitle.BOOKMARK_SCREEN)

    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern = "${NavigationRouteName.DEEP_LINK_SCHEME}$route" }
    )

    companion object {
        fun isMainRoute(route: String?): Boolean {
            return when (route) {
                NavigationRouteName.SEARCH_SCREEN, NavigationRouteName.BOOKMARK_SCREEN -> true
                else -> false
            }
        }
    }
}

/**
 * ImageDetailScreen
 */
object ImageDetailNav : DestinationArg<ImageModel> {
    override val route: String = NavigationRouteName.IMAGE_DETAIL_SCREEN
    override val title: String = NavigationTitle.IMAGE_DETAIL_SCREEN
    override val argName: String = "imageModel"
    override val icon: Int? = null
    override val deepLinks: List<NavDeepLink> = listOf(
        navDeepLink { uriPattern ="${NavigationRouteName.DEEP_LINK_SCHEME}$route/{$argName}" }
    )
    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(argName) { type = NavType.StringType }
    )

    override fun navigateWithArg(item: ImageModel): String {
        val arg = GsonUtils.toJson(item)
        return "$route/$arg"
    }

    override fun findArgument(navBackStackEntry: NavBackStackEntry): ImageModel? {
        val imageString = navBackStackEntry.arguments?.getString(argName)
        return GsonUtils.fromJson<ImageModel>(imageString)
    }
}

/**
 * 목적지의 route, title, deepLinks를 포함하는 interface
 */
interface Destination {
    val route: String
    val title: String
    val deepLinks: List<NavDeepLink>
    val icon: Int?
}

/**
 * 목적지(Screen)로 이동할 때, Screen으로 arguments를 전달하기 위한 Interface
 */
interface DestinationArg<T> : Destination {
    val argName: String
    val arguments: List<NamedNavArgument>

    fun routeWithArgName() = "$route/{$argName}"
    fun navigateWithArg(item: T): String
    fun findArgument(navBackStackEntry: NavBackStackEntry): T?
}

object NavigationRouteName {
    const val DEEP_LINK_SCHEME = "choimaro://"
    const val SEARCH_SCREEN = "search_screen"
    const val BOOKMARK_SCREEN = "bookmark_screen"
    const val IMAGE_DETAIL_SCREEN = "image_detail_screen"
}

object NavigationTitle {
    const val SEARCH_SCREEN = "검색"
    const val BOOKMARK_SCREEN = "북마크"
    const val IMAGE_DETAIL_SCREEN = "상세페이지"
}