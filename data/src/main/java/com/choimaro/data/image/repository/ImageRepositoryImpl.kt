package com.choimaro.data.image.repository

import android.util.Log
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.choimaro.data.db.entity.ImageBookMarkEntity
import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.remote.RemoteDataSource
import com.choimaro.domain.ResponseState
import com.choimaro.domain.image.repository.ImageRepository
import com.choimaro.domain.model.ImageModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : ImageRepository {
    override suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ResponseState> = flow {
        emit(ResponseState.Loading)
        val localFlow = localDataSource.getAllImageBookMark()
        val remoteFlow = remoteDataSource.getImageSearchResult(query, sort, page, size)
        remoteFlow.combine(localFlow) { responseState, imageBookMarkList ->
            when (responseState) {
                is ResponseState.Success<*> -> {
                    val data = responseState.data as List<ImageModel>
                    val updatedList = data.map { imageModel ->
                        updateBookMarkStatus(imageModel, imageBookMarkList)
                    }
                    ResponseState.Success(updatedList)
                }

                is ResponseState.Fail -> {
                    ResponseState.Fail(responseState.exception)
                }

                ResponseState.Init -> {}
                ResponseState.Loading -> {}
            }
        }.catch { e ->
            emit(ResponseState.Fail(e.message ?: "Unknown Error"))
        }
    }

    override fun getImageSearchResultFlow(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<PagingData<ImageModel>> {
        return remoteDataSource.getImageSearchResultFlow(query, sort, page, size)
    }

    private fun updateBookMarkStatus(imageModel: ImageModel, bookMarkList: List<ImageBookMarkEntity>): ImageModel {
        val bookMarkIds = bookMarkList.map { it.id }
        return imageModel.copy(isCheckedBookMark = bookMarkIds.contains(imageModel.id))
    }

    /**
     * 두 Flow를 Impl 단에서 combine하는 예시 함수
     */
//    override fun getImageSearchResultFlow(
//        query: String,
//        sort: String,
//        page: Int,
//        size: Int
//    ): Flow<PagingData<ImageModel>> {
//        val remoteFlow = remoteDataSource.getImageSearchResultFlow(query, sort, page, size)
//        val localFlow = localDataSource.getAllImageBookMark()
//
//        return remoteFlow
//            .combine(localFlow) { imageModelList, imageBookMarkList ->
//                imageModelList.map { imageModel ->
//                    updateBookMarkStatus(imageModel, imageBookMarkList)
//                }
//            }
//    }
}