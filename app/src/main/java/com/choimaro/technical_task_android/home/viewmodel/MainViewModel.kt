package com.choimaro.technical_task_android.home.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choimaro.domain.ResponseState
import com.choimaro.domain.image.usecase.image.GetImageSearchUseCase
import com.choimaro.domain.model.image.ImageDocument
import com.choimaro.domain.model.image.ImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImageSearchUseCase: GetImageSearchUseCase
): ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _imageSearchResult = MutableStateFlow(ResponseState.init())
    val imageSearchResult = _imageSearchResult.asStateFlow()

    private val _imageModelList = MutableStateFlow(arrayListOf<ImageModel>())
    val imageModelList = _imageModelList.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _isReady.value = true
        }
    }
    fun getImageSearchResult(searchText: String) = viewModelScope.launch {
        if (searchText.isNotEmpty()) {
            _imageSearchResult.value = ResponseState.Loading
            if (searchText.isNotEmpty()) {
                try {
                    val result = getImageSearchUseCase(query = searchText, sort = "accuracy", page = 1, size = 20)
                    _imageSearchResult.value = result
                } catch (e: Exception) {

                }
            }
        } else {
            _imageSearchResult.value = ResponseState.init()
        }
    }
    fun setSearchText(searchText: String) {
        _searchText.value = searchText
    }
    fun setFavorite(imageModel: ImageModel) {
        imageSearchResult.value
    }
    fun setImageModelList(imageModelList: List<ImageModel>) {
        _imageModelList.value = ArrayList(imageModelList)
    }
}