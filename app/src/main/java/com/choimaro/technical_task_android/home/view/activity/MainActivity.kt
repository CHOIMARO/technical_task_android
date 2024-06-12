package com.choimaro.technical_task_android.home.view.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.choimaro.technical_task_android.home.view.screen.MainScreen
import com.choimaro.technical_task_android.ui.theme.TechnicalTaskAndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            TechnicalTaskAndroidTheme {
                MainScreen()
            }
        }
    }
}
