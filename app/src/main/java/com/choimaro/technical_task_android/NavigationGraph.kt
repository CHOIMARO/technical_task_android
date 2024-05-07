package com.choimaro.technical_task_android

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.choimaro.technical_task_android.home.view.screen.BookMarkScreen
import com.choimaro.technical_task_android.home.view.screen.SearchScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreenType.SearchScreen.route) {
        composable(route = ScreenType.SearchScreen.route) {
            SearchScreen(navController)
        }
        composable(route = ScreenType.BookMarkScreen.route) {
            BookMarkScreen(navController)
        }
    }
}