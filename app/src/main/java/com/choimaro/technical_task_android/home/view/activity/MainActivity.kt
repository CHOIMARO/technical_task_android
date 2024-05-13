package com.choimaro.technical_task_android.home.view.activity

import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
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
import com.choimaro.technical_task_android.util.ToastManager
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
        Box(Modifier.padding(it)){
            NavigationGraph(navController = navController, mainViewModel)
        }
        ShowInsertBookMarkResultToast(mainViewModel = mainViewModel)
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
            Text(
                text =
                    when (currentRoute) {
                        ScreenType.SearchScreen.route -> {
                            stringResource(id = R.string.search)
                        }
                        ScreenType.BookMarkScreen.route -> {
                            stringResource(id = R.string.book_mark)
                        }
                        else -> {""}
                    }
            )
        },
        actions = {
            // 편집 버튼 추가
            if (currentRoute == ScreenType.BookMarkScreen.route) {
                if (isClickedEditButton) {
                    Row {
                        DeleteBookMarkContent(mainViewModel)
                        Button(
                            onClick = { mainViewModel.clickEditButton() }
                        ) {
                            Text(text = "완료")
                        }
                    }
                } else {
                    IconButton(
                        onClick = { mainViewModel.clickEditButton() },
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_edit), // 이 부분에서 자신의 편집 아이콘 리소스를 사용하세요
                            contentDescription = "Edit"
                        )
                    }
                }
            }
        }
    )
}
@Composable
fun DeleteBookMarkContent(mainViewModel: MainViewModel = hiltViewModel()) {
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
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = stringResource(id = R.string.delete))
            }
        }
    }
}

@Composable
fun BottomBarNavigation(navController: NavHostController) {
    val items = listOf(
        ScreenType.SearchScreen,
        ScreenType.BookMarkScreen
    )
    NavigationBar(
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEach { item ->
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
    }
}
@Composable
fun ShowInsertBookMarkResultToast(mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val toastManager = remember { ToastManager(context) }
    val insertBookMarkResult by mainViewModel.insertBookMarkResult.collectAsState()
    LaunchedEffect(key1 = insertBookMarkResult) {
        when (insertBookMarkResult) {
            true -> toastManager.showToast("북마크 등록 성공", Toast.LENGTH_SHORT)
            false -> toastManager.showToast("북마크 등록 실패", Toast.LENGTH_SHORT)
            null -> {} // null 상태에 대한 처리 (예: 아무 것도 하지 않음)
        }
        mainViewModel.resetInsertBookMarkResult()
    }
}
