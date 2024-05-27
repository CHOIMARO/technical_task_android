package com.choimaro.domain.image.repository

import androidx.paging.PagingData
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
    suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ResponseState>

    fun getImageSearchResultFlow(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<PagingData<ImageModel>>
}