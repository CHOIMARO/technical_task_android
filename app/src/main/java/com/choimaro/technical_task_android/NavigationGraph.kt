package com.choimaro.technical_task_android

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.choimaro.technical_task_android.home.view.screen.BookMarkScreen
import com.choimaro.technical_task_android.home.view.screen.SearchScreen
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    NavHost(
        navController = navController,
        startDestination = ScreenType.SearchScreen.route) {
        composable(route = ScreenType.SearchScreen.route) { backStackEntry ->
            SearchScreen(navController, mainViewModel)
        }
        composable(route = ScreenType.BookMarkScreen.route) { backStackEntry ->
            BookMarkScreen(navController, mainViewModel)
        }
    }
}