package com.choimaro.domain.image.repository

import androidx.paging.PagingData
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.image.ImageModel
import kotlinx.coroutines.flow.Flow

interface ImageRepository {
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