package com.choimaro.technical_task_android.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.choimaro.domain.ResponseState
import com.choimaro.domain.entity.BookMarkEntity
import com.choimaro.domain.image.usecase.db.bookmark.DeleteBookMarkUseCase
import com.choimaro.domain.image.usecase.db.bookmark.GetAllBookMarkUseCase
import com.choimaro.domain.image.usecase.db.bookmark.InsertBookMarkUseCase
import com.choimaro.domain.image.usecase.image.GetImageSearchUseCase
import com.choimaro.domain.model.image.ImageModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getImageSearchUseCase: GetImageSearchUseCase,
    private val getAllBookMarkUseCase: GetAllBookMarkUseCase,
    private val insertBookMarkUseCase: InsertBookMarkUseCase,
    private val deleteBookMarkUseCase: DeleteBookMarkUseCase
): ViewModel() {
    private val _isReady = MutableStateFlow(false)
    val isReady = _isReady.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchText = _searchText.asStateFlow()

    private val _imageSearchResult = MutableStateFlow(ResponseState.init())
    val imageSearchResult = _imageSearchResult.asStateFlow()

    private val _imageModelList = MutableStateFlow(listOf<ImageModel>())
    val imageModelList = _imageModelList.asStateFlow()

    private val _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private val _bookMarkList = MutableStateFlow(listOf<ImageModel>())
    val bookMarkList = _bookMarkList.asStateFlow()

    private val _checkedBookMarkList = MutableStateFlow(listOf<String>())
    val checkedBookMarkList = _checkedBookMarkList.asStateFlow()

    private val _isClickEditButton = MutableStateFlow(false)
    val isClickEditButton = _isClickEditButton.asStateFlow()

    private val _insertBookMarkResult = MutableStateFlow<Boolean?>(null)
    val insertBookMarkResult = _insertBookMarkResult.asStateFlow()

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
        if (imageModel.isCheckedBookMark) {
            deleteBookMark(listOf(imageModel.id))
        } else {
            insertBookMark(imageModel)
        }
        _imageModelList.update { currentList ->
            currentList.map { existingModel ->
                if (existingModel.id == imageModel.id) {
                    existingModel.copy(isCheckedBookMark = !existingModel.isCheckedBookMark)
                } else {
                    existingModel
                }
            }
        }
    }
    fun setImageModelList(imageModelList: List<ImageModel>) {
        _imageModelList.value = ArrayList(imageModelList)
    }
    fun getAllBookMark() = viewModelScope.launch {
        _bookMarkList.value = getAllBookMarkUseCase().map {
            mapBookMarkEntityToImageModel(it)
        }
    }
    private fun insertBookMark(imageModel: ImageModel) = viewModelScope.launch {
        try {
            val result = insertBookMarkUseCase(mapImageModelToBookMarkEntity(imageModel))
            if (result) {
                _insertBookMarkResult.value = true
            }
        } catch (e: Exception) {

        }
    }
    fun deleteBookMark(ids: List<String>) = viewModelScope.launch {
        val result = deleteBookMarkUseCase(ids)
        if (result) {
            initializeCheckedBookMarkList()
            getAllBookMark()
        } else {

        }

    }
    fun initializeCheckedBookMarkList() {
        _checkedBookMarkList.value = listOf()
    }
    private fun mapImageModelToBookMarkEntity(imageModel: ImageModel): BookMarkEntity {
        return BookMarkEntity(
            id = imageModel.id,
            thumbnailUrl = imageModel.thumbnailUrl,
            imageUrl = imageModel.imageUrl,
            displaySiteName = imageModel.displaySiteName,
            datetime = imageModel.datetime,
            itemType = imageModel.itemType,
            docUrl = imageModel.docUrl
        )
    }
    private fun mapBookMarkEntityToImageModel(bookMarkEntity: BookMarkEntity): ImageModel {
        return ImageModel(
            id = bookMarkEntity.id!!,
            thumbnailUrl = bookMarkEntity.thumbnailUrl,
            imageUrl = bookMarkEntity.imageUrl,
            displaySiteName = bookMarkEntity.displaySiteName,
            datetime = bookMarkEntity.datetime,
            itemType = bookMarkEntity.itemType!!,
            docUrl = bookMarkEntity.docUrl
        )
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

    fun initialize() {
        _imageSearchResult.value = ResponseState.init()
    }
    fun resetInsertBookMarkResult() {
        _insertBookMarkResult.value = null // 상태를 null로 리셋
    }
}