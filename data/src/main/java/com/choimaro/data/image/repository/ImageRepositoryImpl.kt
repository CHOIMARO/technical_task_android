package com.choimaro.data.image.repository

import androidx.paging.PagingData
import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.remote.RemoteDataSource
import com.choimaro.domain.ResponseState
import com.choimaro.domain.entity.BookMarkEntity
import com.choimaro.domain.image.repository.ImageRepository
import com.choimaro.domain.model.image.ImageModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher
): ImageRepository {
    override suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ResponseState = withContext(ioDispatcher){
        return@withContext remoteDataSource.getImageSearchResult(query, sort, page, size)
    }

    override suspend fun getImageSearchResult2(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<PagingData<ImageModel>> = withContext(ioDispatcher){
        return@withContext remoteDataSource.getImageSearchResult2(query, sort, page, size)
    }
}