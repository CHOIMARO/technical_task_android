package com.choimaro.data.image.repository

import com.choimaro.data.remote.RemoteDataSource
import com.choimaro.domain.ResponseState
import com.choimaro.domain.image.repository.ImageRepository
import kotlinx.coroutines.CoroutineDispatcher
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
}