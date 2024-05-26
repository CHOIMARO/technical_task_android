package com.choimaro.technical_task_android.home.view.screen

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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.choimaro.domain.model.ImageModel
import com.choimaro.technical_task_android.component.ImageModelItem
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel

@Composable
fun BookMarkScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    initialize(mainViewModel)
    Column(modifier = Modifier.fillMaxSize()) {
        BookMarkScreenStateContent(mainViewModel)
    }
}

@Composable
fun initialize(viewModel: MainViewModel) {
    viewModel.initialize()
}

@Composable
fun BookMarkScreenStateContent(viewModel: MainViewModel) {
    viewModel.getAllBookMark()
    val bookMarkList by viewModel.bookMarkList.collectAsState()
    val isClickedEditButton by viewModel.isClickEditButton.collectAsState()
    val checkedBookMarkList by viewModel.checkedBookMarkList.collectAsState()
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(bookMarkList) { imageModel ->
            BookMarkItem(viewModel, imageModel,isClickedEditButton, checkedBookMarkList)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMarkItem(
    viewModel: MainViewModel,
    imageModel: ImageModel,
    isClickedEditButton: Boolean,
    checkedBookMarkList: List<String>
) {
    Card(
        modifier = Modifier
            .wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp),
        onClick = {
            if (isClickedEditButton) {
                viewModel.toggleBookMarkChecked(imageModel.id)
            }
        }
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            ImageModelItem(
                imageModel = imageModel, imageModifier = Modifier
                .height(130.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
                )
            if (isClickedEditButton) {
                Checkbox(
                    checked = checkedBookMarkList.contains(imageModel.id),
                    onCheckedChange = {
                        viewModel.toggleBookMarkChecked(imageModel.id)
                        viewModel.getAllBookMark()
                    },
                )
            }

        }
    }
}