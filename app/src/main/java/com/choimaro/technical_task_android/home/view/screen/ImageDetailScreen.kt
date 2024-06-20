package com.choimaro.technical_task_android.home.view.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.choimaro.domain.model.ImageModel
import com.choimaro.technical_task_android.component.ImageModelItem
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel

@Composable
fun ImageDetailScreen(
    imageModel: ImageModel,
    navHostController: NavHostController,
    viewModel: MainViewModel = hiltViewModel()) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ){
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            val screenHeight = maxHeight
            val halfScreenHeight = screenHeight / 2

            ImageModelItem(
                imageModel = imageModel,
                imageModifier = Modifier
                    .height(halfScreenHeight)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
            )
        }
    }
}