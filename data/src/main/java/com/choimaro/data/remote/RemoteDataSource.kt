package com.choimaro.data.remote

import com.choimaro.domain.ResponseState

interface RemoteDataSource {
    suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ResponseState
}