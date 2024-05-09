package com.choimaro.technical_task_android.home.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.choimaro.domain.model.image.ImageModel
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel

@Composable
fun BookMarkScreen(navHostController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    viewModel.getAllBookMark()
    Column(modifier = Modifier.fillMaxSize()) {
        BookMarkScreenStateContent(viewModel)
    }
}

@Composable
fun BookMarkScreenStateContent(viewModel: MainViewModel = hiltViewModel()) {
    val checkedBookMarkList by viewModel.checkedBookMarkList.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(checkedBookMarkList) { imageModel ->
            BookMarkItem(imageModel = imageModel, viewModel)
        }
    }
}

@Composable
fun BookMarkItem(
    imageModel: ImageModel,
    viewModel: MainViewModel = hiltViewModel()
) {
    Card(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.TopEnd
//        ) {
//            IconButton(
//                onClick = {
//                    viewModel.setFavorite(imageModel)
//                    viewModel.getAllBookMark()
//                }
//            ) {
//                Icon(
//                    imageVector = if (imageModel.isCheckedBookMark) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
//                    contentDescription = "",
//                    tint = Color.Black
//                )
//            }
//        }
        Column(
            modifier = Modifier
                .padding(8.dp)
                .wrapContentSize()
        ) {
            Image(
                painter = rememberAsyncImagePainter(imageModel.imageUrl),
                contentDescription = "",
                modifier = Modifier
                    .height(130.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Text(text = "${imageModel.displaySiteName}")
            Text(text = imageModel.datetime!!)
        }
    }
}