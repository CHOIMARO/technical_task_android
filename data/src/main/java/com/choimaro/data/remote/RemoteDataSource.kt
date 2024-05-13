package com.choimaro.data.remote

import androidx.paging.PagingData
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.image.ImageModel
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {
    suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ResponseState

    suspend fun getImageSearchResultFlow(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<PagingData<ImageModel>>
}