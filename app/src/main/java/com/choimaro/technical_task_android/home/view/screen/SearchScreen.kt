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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.image.ImageModel
import com.choimaro.technical_task_android.R
import com.choimaro.technical_task_android.component.ImageModelItem
import com.choimaro.technical_task_android.home.viewmodel.MainViewModel
import com.choimaro.technical_task_android.ui.theme.TechnicalTaskAndroidTypography
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navHostController: NavHostController, mainViewModel: MainViewModel) {
    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(mainViewModel)
        Spacer(modifier = Modifier.height(8.dp))
        SearchResult(mainViewModel)
    }
}
@Composable
fun SearchBar(
    viewModel: MainViewModel
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
                launch { viewModel.getImageSearchFlowResult(trimmedText) }
                lastValidSearch = trimmedText
            }
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SearchTextField(searchText, viewModel, clearButtonVisible, keyboardController)
    }
}

@Composable
private fun SearchTextField(
    searchText: String,
    viewModel: MainViewModel,
    clearButtonVisible: Boolean,
    keyboardController: SoftwareKeyboardController?
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = { value ->
            viewModel.setSearchText(value)
        },
        modifier = Modifier
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
@Composable
private fun SearchResult(mainViewModel: MainViewModel) {
    val imageResults = mainViewModel.imageModelResults.collectAsLazyPagingItems()
    val state = imageResults.loadState
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (state.refresh is LoadState.Loading) {
            HandleInitResponse(mainViewModel)
        } else if (state.append is LoadState.Loading) {
            CircularProgressIndicator()
        } else if (state.refresh is LoadState.Error){
            HandleFailResponse()
        } else if (state.append is LoadState.Error) {
            HandleFailResponse()
        } else {
            HandleSuccessResponse(mainViewModel, imageResults)
        }
    }
}
@Composable
fun HandleSuccessResponse(viewModel: MainViewModel, imageResults: LazyPagingItems<ImageModel>) {
    val bookMarkList by viewModel.bookMarkList.collectAsState()
    if (imageResults.itemCount > 0) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .fillMaxSize(),
        ) {

            items(imageResults.itemCount) { index ->
                imageResults[index]?.let { imageModel ->
                    ImageDocumentItem(imageModel, bookMarkList) {
                        viewModel.setFavorite(imageResults[index]!!)
                    }
                }
            }
            item {
                if (imageResults.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator()
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
                Text(stringResource(id = R.string.there_are_no_search_results))
            }
        }
    }
}
@Composable
fun ImageDocumentItem(
    imageModel: ImageModel,
    bookMarkList: List<ImageModel>,
    clickIconButton: () -> Unit
) {
    Card(
        modifier = Modifier.wrapContentSize(),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        BookMarkIcon(clickIconButton, imageModel, bookMarkList)
        ImageModelItem(
            imageModel = imageModel, imageModifier = Modifier
                .height(130.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(8.dp))
        )
    }
}
@Composable
private fun BookMarkIcon(
    clickIconButton: () -> Unit,
    imageModel: ImageModel,
    bookMarkList: List<ImageModel>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopEnd
    ) {
        IconButton(
            onClick = clickIconButton
        ) {
            Icon(
                imageVector = if (bookMarkList.any { it.id == imageModel.id }) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "",
                tint = Color.Black
            )
        }
    }
}

@Composable
private fun HandleFailResponse() {
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
                stringResource(id = R.string.the_connection_is_not_smooth),
                style = TechnicalTaskAndroidTypography.bodyLarge
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                stringResource(id = R.string.please_try_again_in_a_moment),
                style = TechnicalTaskAndroidTypography.bodyMedium
            )
        }
    }
}
@Composable
private fun HandleInitResponse(mainViewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Text(stringResource(id = R.string.please_enter_a_search_term))
        }
    }
}