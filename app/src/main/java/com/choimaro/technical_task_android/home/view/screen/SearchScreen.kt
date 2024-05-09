package com.choimaro.technical_task_android.home.view.screen

import android.media.Image
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.SearchResponse
import com.choimaro.domain.model.image.ImageDocument
import com.choimaro.domain.model.image.ImageModel
import com.choimaro.technical_task_android.R
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.ArrayList
@Composable
fun SearchScreen(navHostController: NavHostController, viewModel: MainViewModel = hiltViewModel()) {
    val state by viewModel.imageSearchResult.collectAsState()
    val imageModelList by viewModel.imageModelList.collectAsState()
    HandleImageSearchResult(state = state, viewModel)
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(viewModel) { searchText ->
            viewModel.getImageSearchResult(searchText)
        }
        SearchScreenStateContent(imageModelList = imageModelList, viewModel)
    }
}

@Composable
fun HandleImageSearchResult(state: ResponseState, viewModel: MainViewModel = hiltViewModel()) {
    when (state) {
        is ResponseState.Loading -> {
            Log.e(">>>>>", "SearchScreen: LOADING")
            // 로딩 상태 UI 표시
            CircularProgressIndicator()
        }
        is ResponseState.Success<*> -> {
            Log.e(">>>>>", "SearchScreen: SUCCESS")
            // 성공 상태 UI 표시
            // 예: response.data를 사용하여 데이터 표시
//            ImageDocumentList(state.data as List<ImageModel>)
            viewModel.setImageModelList(state.data as List<ImageModel>)
        }
        is ResponseState.Fail -> {
            Log.e(">>>>>", "SearchScreen: FAILED")
            // 실패 상태 UI 표시
            // 예: response.exception 메시지 표시
            Text("Error: ${state.exception}")
        }
        is ResponseState.Init -> {
            Log.e(">>>>>", "SearchScreen: INIT")
            // TODO 검색어 입력 요청 Screen 추가하기
            viewModel.setImageModelList(arrayListOf())
        }
    }
}
@Composable
fun SearchScreenStateContent(imageModelList: ArrayList<ImageModel>, viewModel: MainViewModel = hiltViewModel()) {
    val address = viewModel
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        items(imageModelList) { imageModel ->
            ImageDocumentItem(imageModel = imageModel, viewModel)
        }
    }
}

@Composable
fun ImageDocumentItem(
    imageModel: ImageModel,
    viewModel: MainViewModel = hiltViewModel()
) {
    val isFavorite by viewModel.isFavorite.collectAsState()
    Card(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
        ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.TopEnd
        ) {
            IconButton(
                onClick = {
                    viewModel.setFavorite(imageModel)
                }
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "",
                    tint = Color.Black
                )
            }
        }
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

@Composable
fun SearchBar(
    viewModel: MainViewModel = hiltViewModel(),
    onTextChanged: (String) -> Unit
) {
    val searchText by viewModel.searchText.collectAsState()
    val clearButtonVisible by remember { derivedStateOf { searchText.isNotEmpty() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    var job by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchText) {
        job?.cancel()  // 이전 작업이 있다면 취소
        job = coroutineScope.launch {
            delay(1000)  // 1초 대기
            onTextChanged(searchText)  // Delay 이후 실행할 작업
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = {
                value -> viewModel.setSearchText(value)
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            placeholder = { Text(text = stringResource(id = R.string.search)) },
            singleLine = true,
            trailingIcon = {
                if (clearButtonVisible) {
                    IconButton(onClick = { viewModel.setSearchText("")}) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_clear_circle),
                            contentDescription = "Clear",
                            tint = Color.Gray
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    keyboardController?.hide()
                }
            )
        )
    }
}

@Composable
fun SearchedList() {

}