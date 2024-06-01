package com.choimaro.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.service.KakaoService
import com.choimaro.data.util.Utils
import com.choimaro.domain.ResponseState
import com.choimaro.domain.extensions.getFormattedDate
import com.choimaro.domain.model.ErrorResponse
import com.choimaro.domain.model.ImageModel
import com.choimaro.domain.model.SearchListType
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val kakaoService: KakaoService
) {
    suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<ResponseState> = flow {
        try {
            kakaoService.searchImage(query, sort, page, size).let { response ->
                if (response.isSuccessful) {
                    val imageModelList = response.body()?.documents?.map { imageDocument ->
                        ImageModel(
                            thumbnailUrl = imageDocument.thumbnailUrl ?: "",
                            imageUrl = imageDocument.imageUrl ?: "",
                            displaySiteName = imageDocument.displaySiteName ?: "",
                            datetime = imageDocument.dateTime?.getFormattedDate() ?: "Unknown date",
                            docUrl = imageDocument.docUrl ?: "",
                            id = Utils.generateHash(imageDocument.imageUrl + imageDocument.docUrl + SearchListType.IMAGE.name)
                        )
                    } ?: arrayListOf()
                    emit(ResponseState.Success(imageModelList))
                } else {
                    val errorResponse = response.errorBody()?.string()?.let {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    }
                    emit(ResponseState.Fail(errorResponse?.message ?: ""))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emit(ResponseState.Fail(""))
        }
    }
    fun getImageSearchResultFlow(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): Flow<PagingData<ImageModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = size,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PagingSource(kakaoService, query, sort, size) }
        ).flow
    }
}