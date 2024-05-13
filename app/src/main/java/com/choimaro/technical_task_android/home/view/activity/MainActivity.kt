package com.choimaro.technical_task_android.home.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
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
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.choimaro.technical_task_android.NavigationGraph
import com.choimaro.technical_task_android.R
import com.choimaro.technical_task_android.ScreenType
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel
import com.choimaro.technical_task_android.ui.theme.TechnicalTaskAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TechnicalTaskAndroidTheme {
                MainScreenView()
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            TopBar(navController = navController, mainViewModel)
        },
        bottomBar = { BottomBarNavigation(navController = navController) }
    ) {
        Box(Modifier.padding(it)) {
            NavigationGraph(navController = navController, mainViewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    navController: NavHostController,
    mainViewModel: MainViewModel
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isClickedEditButton by mainViewModel.isClickEditButton.collectAsState()
    TopAppBar(
        title = {
            TopAppBarTitle(currentRoute)
        },
        actions = {
            // 편집 버튼 추가
            TopAppBarAction(currentRoute, isClickedEditButton, mainViewModel)
        }
    )
}
@Composable
private fun TopAppBarTitle(currentRoute: String?) {
    Text(
        text =
        when (currentRoute) {
            ScreenType.SearchScreen.route -> {
                stringResource(id = R.string.search)
            }

            ScreenType.BookMarkScreen.route -> {
                stringResource(id = R.string.book_mark)
            }

            else -> {
                ""
            }
        }
    )
}

@Composable
private fun TopAppBarAction(
    currentRoute: String?,
    isClickedEditButton: Boolean,
    mainViewModel: MainViewModel
) {
    if (currentRoute == ScreenType.BookMarkScreen.route) {
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
fun DeleteBookMarkButton(mainViewModel: MainViewModel) {
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
fun BottomBarNavigation(navController: NavHostController) {
    val items = listOf(
        ScreenType.SearchScreen,
        ScreenType.BookMarkScreen
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
    item: ScreenType,
    currentRoute: String?,
    navController: NavHostController
) {
    NavigationBarItem(
        icon = {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(id = item.title),
                modifier = Modifier
                    .width(26.dp)
                    .height(26.dp)
            )
        },
        label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
        selected = currentRoute == item.route,
        alwaysShowLabel = false,
        onClick = {
            // 현재 스크린과 다를 때만 navigate 실행
            if (currentRoute != item.route) {
                navController.navigate(item.route) {
                    // 동일한 스크린을 재생성하지 않고, 최상위에 올림
                    launchSingleTop = true
                    // 이미 스택에 있는 스크린으로 이동할 경우, 그 스크린을 최상위로 만들고, 그 위에 있던 스크린을 모두 제거
                    restoreState = true
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                }
            }
        }
    )
}
