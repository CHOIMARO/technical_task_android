package com.choimaro.technical_task_android.home.view.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.choimaro.domain.model.ImageModel
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel

@Composable
fun ImageDetailScreen(
    imageModel: ImageModel,
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = "ImageDetailScreen")
    }
}