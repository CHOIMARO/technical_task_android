package com.choimaro.technical_task_android.home.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import androidx.paging.map
import com.choimaro.data.db.entity.ImageBookMarkEntity
import com.choimaro.domain.ResponseState
import com.choimaro.domain.image.usecase.db.bookmark.DeleteBookMarkUseCase
import com.choimaro.domain.image.usecase.db.bookmark.GetAllBookMarkUseCase
import com.choimaro.domain.image.usecase.db.bookmark.InsertBookMarkUseCase
import com.choimaro.domain.image.usecase.image.GetImageSearchFlowUseCase
import com.choimaro.domain.model.ImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImageSearchFlowUseCase: GetImageSearchFlowUseCase,
    private val getAllBookMarkUseCase: GetAllBookMarkUseCase,
    private val insertBookMarkUseCase: InsertBookMarkUseCase,
    private val deleteBookMarkUseCase: DeleteBookMarkUseCase
) : ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _bookMarkList = MutableStateFlow(listOf<ImageModel>())
    val bookMarkList = _bookMarkList.asStateFlow()

    private val _checkedBookMarkList = MutableStateFlow(listOf<String>())
    val checkedBookMarkList = _checkedBookMarkList.asStateFlow()

    private val _isClickEditButton = MutableStateFlow(false)
    val isClickEditButton = _isClickEditButton.asStateFlow()

    private val _imageModelResults = MutableStateFlow<PagingData<ImageModel>>(PagingData.empty())
    val imageModelResults = _imageModelResults.asStateFlow()

    init {
        viewModelScope.launch {
            delay(1000)
            _isReady.value = true
        }
    }

    suspend fun getImageSearchFlowResult(searchText: String) = viewModelScope.launch {
        if (searchText.isNotEmpty()) {
            try {
                val getImageSearchFlow = getImageSearchFlowUseCase(
                    query = searchText,
                    sort = "accuracy",
                    page = 1,
                    size = 5
                ).cachedIn(viewModelScope)
                val imageBookMarkFlow = getAllBookMarkUseCase()

                getImageSearchFlow.combine(imageBookMarkFlow) { imageModelList, bookMarkList ->
                    imageModelList.map { imageModel ->
                        updateBookMarkStatus(imageModel, bookMarkList)
                     }
                }.cachedIn(viewModelScope).collectLatest {
                    _imageModelResults.emit(it)
                }

            } catch (e: Exception) {
                Log.e(">>>>>", "${e.message}")
            }
        } else {
            _imageModelResults.emit(
                PagingData.empty(
                    LoadStates(
                        refresh = LoadState.Loading,
                        prepend = LoadState.NotLoading(true),
                        append = LoadState.NotLoading(true)
                    )
                )
            )
        }
    }
    private fun updateBookMarkStatus(imageModel: ImageModel, bookMarkList: List<ImageModel>): ImageModel {
        val bookMarkIds = bookMarkList.map { it.id }
        return imageModel.copy(isCheckedBookMark = bookMarkIds.contains(imageModel.id))
    }

    fun setSearchText(searchText: String) {
        _searchText.value = searchText
    }

    fun setFavorite(imageModel: ImageModel) {
        viewModelScope.launch {
            if (_bookMarkList.value.any { it.id == imageModel.id }) {
                deleteBookMark(listOf(imageModel.id))
            } else {
                insertBookMark(imageModel)
            }
        }
    }

    fun getAllBookMark() = viewModelScope.launch {
        try {
            getAllBookMarkUseCase().collectLatest {
                _bookMarkList.emit(it)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun insertBookMark(imageModel: ImageModel) = viewModelScope.launch {
        try {
            val result = insertBookMarkUseCase(imageModel)
            if (result) {
                getAllBookMark()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deleteBookMark(ids: List<String>) = viewModelScope.launch {
        try {
            val result = deleteBookMarkUseCase(ids)
            if (result) {
                initializeCheckedBookMarkList()
                getAllBookMark()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initializeCheckedBookMarkList() {
        _checkedBookMarkList.value = listOf()
    }

    fun clickEditButton() {
        _isClickEditButton.value = !_isClickEditButton.value
        initializeCheckedBookMarkList()
    }

    fun toggleBookMarkChecked(id: String) {
        val currentIds = _checkedBookMarkList.value
        if (currentIds.contains(id)) {
            _checkedBookMarkList.value = currentIds - id
        } else {
            _checkedBookMarkList.value = currentIds + id
        }
    }
}