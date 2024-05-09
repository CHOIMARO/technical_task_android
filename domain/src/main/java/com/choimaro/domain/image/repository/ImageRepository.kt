package com.choimaro.domain.image.repository

import com.choimaro.domain.ResponseState

interface ImageRepository {
    suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ResponseState
}