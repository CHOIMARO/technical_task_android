package com.choimaro.technical_task_android.home.view.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.image.ImageModel
import com.choimaro.technical_task_android.R
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel
import com.choimaro.technical_task_android.ui.theme.TechnicalTaskAndroidTypography
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    val state by mainViewModel.imageSearchResult.collectAsState()
    HandleImageSearchResult(mainViewModel)
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(mainViewModel)
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when (state) {
                is ResponseState.Loading -> {
                    // 로딩 상태 UI 표시
                    CircularProgressIndicator()
                }

                is ResponseState.Success<*> -> {
                    // 성공 상태 UI 표시
                    // 예: response.data를 사용하여 데이터 표시
                    SearchScreenStateContent(mainViewModel)
                }

                is ResponseState.Fail -> {
                    // 실패 상태 UI 표시
                    // 예: response.exception 메시지 표시
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_network_not_available),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                stringResource(id = R.string.you_are_not_connected),
                                style = TechnicalTaskAndroidTypography.bodyLarge
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                stringResource(id = R.string.please_try_again_in_a_few_minutes),
                                style = TechnicalTaskAndroidTypography.bodyMedium
                            )
                        }
                    }
                }

                is ResponseState.Init -> {
                    Box(
                        modifier = Modifier.fillMaxWidth(.5f)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = null,
                                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
                            )
                            Text(stringResource(id = R.string.please_enter_search_input))
                        }
                    }
                    mainViewModel.setImageModelList(arrayListOf())
                }
            }
        }
    }
}

@Composable
fun HandleImageSearchResult(viewModel: MainViewModel) {
    val state by viewModel.imageSearchResult.collectAsState()
    when (state) {
        is ResponseState.Success<*> -> {
            viewModel.setImageModelList((state as ResponseState.Success<*>).data as List<ImageModel>)
        }

        else -> {}
    }
}

@Composable
fun SearchScreenStateContent(viewModel: MainViewModel = hiltViewModel()) {
    val imageModelList by viewModel.imageModelList.collectAsState()

    if (imageModelList.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            items(imageModelList) { imageModel ->
                ImageDocumentItem(imageModel = imageModel) {
                    viewModel.setFavorite(imageModel)
                    viewModel.getAllBookMark()
                }
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxWidth(.5f),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(stringResource(id = R.string.no_results_were_found_for_your_search))
            }
        }
    }
}

@Composable
fun ImageDocumentItem(
    imageModel: ImageModel,
    clickIconButton: () -> Unit
) {
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
                onClick = clickIconButton
            ) {
                Icon(
                    imageVector = if (imageModel.isCheckedBookMark) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
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
    viewModel: MainViewModel = hiltViewModel()
) {
    var lastValidSearch by remember { mutableStateOf("") }
    val searchText by viewModel.searchText.collectAsState()
    val clearButtonVisible by remember { derivedStateOf { searchText.isNotEmpty() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    var job by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchText) {
        val trimmedText = searchText.trim()

        if (trimmedText != lastValidSearch) {
            job?.cancel()
            job = coroutineScope.launch {
                delay(1000)
                viewModel.getImageSearchResult(trimmedText)
                lastValidSearch = trimmedText
            }
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchText,
            onValueChange = { value ->
                viewModel.setSearchText(value)
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            placeholder = { Text(text = stringResource(id = R.string.search)) },
            singleLine = true,
            trailingIcon = {
                if (clearButtonVisible) {
                    IconButton(onClick = { viewModel.setSearchText("") }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_clear_circle),
                            contentDescription = "",
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