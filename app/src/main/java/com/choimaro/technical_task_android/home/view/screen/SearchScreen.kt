package com.choimaro.technical_task_android.home.view.screen

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import com.choimaro.technical_task_android.R
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(navHostController: NavHostController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {
        SearchBar() { searchText ->
            //TODO KAKAO API 불러오기
            Log.e(">>>>>", "$searchText")
        }
    }
}

@Composable
fun SearchBar(
    onTextChanged: (String) -> Unit
) {
    val searchText = remember { mutableStateOf("") }
    val clearButtonVisible by remember { derivedStateOf { searchText.value.isNotEmpty() } }
    val keyboardController = LocalSoftwareKeyboardController.current

    var job by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(searchText.value) {
        job?.cancel()  // 이전 작업이 있다면 취소
        job = coroutineScope.launch {
            delay(1000)  // 1초 대기
            onTextChanged(searchText.value)  // Delay 이후 실행할 작업
        }
    }
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = searchText.value,
            onValueChange = {
                value -> searchText.value = value
            },
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            placeholder = { Text(text = stringResource(id = R.string.search)) },
            singleLine = true,
            trailingIcon = {
                if (clearButtonVisible) {
                    IconButton(onClick = { searchText.value = ""}) {
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