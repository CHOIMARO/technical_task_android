package com.choimaro.technical_task_android.home.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.choimaro.technical_task_android.R
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel
import com.choimaro.technical_task_android.ui.Destination
import com.choimaro.technical_task_android.ui.MainNav
import com.choimaro.technical_task_android.ui.ImageDetailNav
import com.choimaro.technical_task_android.util.NavigationUtils

@Composable
fun MainScreen() {
    val viewModel = hiltViewModel<MainViewModel>()
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            MainHeader(viewModel = viewModel, navController = navController, currentRoute)
        },
        bottomBar = {
            if (MainNav.isMainRoute(currentRoute)) {
                BottomBarNavigation(navController = navController)
            }
        },
    ) {
        Box(Modifier.padding(it)) {
            MainNavigationScreen(viewModel = viewModel, navController = navController)
        }
    }
}

@Composable
fun MainNavigationScreen(viewModel: MainViewModel, navController: NavHostController) {
    NavHost(navController = navController, startDestination = MainNav.Search.route) {
        composable(
            route = MainNav.Search.route,
            deepLinks = MainNav.Search.deepLinks
        ) {
            SearchScreen(navHostController = navController, viewModel = viewModel)
        }
        composable(
            route = MainNav.Bookmark.route,
            deepLinks = MainNav.Bookmark.deepLinks
        ) {
            BookmarkScreen(navHostController = navController, viewModel = viewModel)
        }
        composable(
            route = ImageDetailNav.routeWithArgName(),
            arguments = ImageDetailNav.arguments,
            deepLinks = ImageDetailNav.deepLinks
        ) {
            val imageModel = ImageDetailNav.findArgument(it)
            if (imageModel != null) {
                ImageDetailScreen(navHostController = navController, imageModel = imageModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainHeader(viewModel: MainViewModel, navController: NavHostController, currentRoute: String?) {
    val isClickedEditButton by viewModel.isClickEditButton.collectAsState()
    TopAppBar(
        title = { Text(NavigationUtils.findDestination(currentRoute).title) },
        navigationIcon = {
            if (!MainNav.isMainRoute(currentRoute)) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "back")
                }
            } else {
                null
            }
        },
        actions = {
            TopAppBarAction(currentRoute, isClickedEditButton, viewModel)
        }
    )
}

@Composable
private fun TopAppBarAction(
    currentRoute: String?,
    isClickedEditButton: Boolean,
    mainViewModel: MainViewModel
) {
    if (currentRoute == MainNav.Bookmark.route) {
        if (isClickedEditButton) {
            Row {
                DeleteBookMarkButton(mainViewModel)
                CompleteEditButton(mainViewModel)
            }
        } else {
            EditButton(mainViewModel)
        }
    }
}

@Composable
private fun DeleteBookMarkButton(mainViewModel: MainViewModel) {
    val checkedListSize by mainViewModel.checkedBookMarkList.collectAsState()
    val isClickEditButton by mainViewModel.isClickEditButton.collectAsState()
    if (isClickEditButton) {
        Button(
            modifier = Modifier
                .wrapContentSize()
                .padding(0.dp, 0.dp, 8.dp, 0.dp),
            onClick = { mainViewModel.deleteBookMark(checkedListSize) }
        ) {
            Row {
                Text(text = stringResource(id = R.string.delete_item_count, checkedListSize.size))
            }
        }
    }
}
@Composable
private fun CompleteEditButton(mainViewModel: MainViewModel) {
    Button(
        onClick = { mainViewModel.clickEditButton() }
    ) {
        Text(text = stringResource(id = R.string.complete))
    }
}
@Composable
private fun EditButton(mainViewModel: MainViewModel) {
    IconButton(
        onClick = { mainViewModel.clickEditButton() },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_edit), // 이 부분에서 자신의 편집 아이콘 리소스를 사용하세요
            contentDescription = ""
        )
    }
}
@Composable
private fun BottomBarNavigation(navController: NavHostController) {
    val items = listOf(
        MainNav.Search,
        MainNav.Bookmark
    )
    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
            CreateNavigationBarItem(item, currentRoute, navController)
        }
    }
}

@Composable
private fun RowScope.CreateNavigationBarItem(
    item: Destination,
    currentRoute: String?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            if (item.icon != null) {
                Icon(
                    painter = painterResource(id = item.icon!!),
                    contentDescription = item.title,
                    modifier = Modifier
                        .width(26.dp)
                        .height(26.dp)
                )
            }
        },
        label = { Text(item.title, fontSize = 9.sp) },
        selected = currentRoute == item.route,
        alwaysShowLabel = false,
        onClick = {
            // 현재 스크린과 다를 때만 navigate 실행
            if (currentRoute != item.route) {
                NavigationUtils.navigate(navController, item.route, navController.graph.startDestinationRoute)
            }
        }
    )
}