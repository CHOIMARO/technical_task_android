package com.choimaro.data.remote

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.image.ImageModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface RemoteDataSource {
    suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ResponseState

    suspend fun getImageSearchResult2(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<PagingData<ImageModel>>
}