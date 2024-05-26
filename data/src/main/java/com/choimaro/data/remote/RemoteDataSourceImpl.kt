package com.choimaro.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.service.KakaoService
import com.choimaro.data.util.Utils.generateHash
import com.choimaro.domain.extensions.getFormattedDate
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.ErrorResponse
import com.choimaro.domain.model.SearchListType
import com.choimaro.domain.model.ImageModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val kakaoService: KakaoService
): RemoteDataSource {
    override suspend fun getImageSearchResult(
        query: String,
        sort: String,
        page: Int,
        size: Int
    ): ResponseState {
        try {
            kakaoService.searchImage(query, sort, page, size).let { response ->
                if (response.isSuccessful) {
                    val checkedBookMarkList = localDataSource.getAllBookMark()
                    val imageModelList = response.body()?.documents?.map { imageDocument ->
                        ImageModel(
                            thumbnailUrl = imageDocument.thumbnailUrl ?: "",
                            imageUrl = imageDocument.imageUrl ?: "",
                            displaySiteName = imageDocument.displaySiteName ?: "",
                            datetime = imageDocument.dateTime?.getFormattedDate() ?: "Unknown date",
                            docUrl = imageDocument.docUrl ?: "",
                            isCheckedBookMark = checkedBookMarkList.any { it.id == generateHash(imageDocument.imageUrl + imageDocument.docUrl + SearchListType.IMAGE.name) },
                            id = generateHash(imageDocument.imageUrl + imageDocument.docUrl + SearchListType.IMAGE.name)
                        )
                    } ?: arrayListOf()

                    return ResponseState.Success(imageModelList)
                } else {
                    val errorResponse = response.errorBody()?.string()?.let {
                        Gson().fromJson(it, ErrorResponse::class.java)
                    }
                    return ResponseState.Fail( errorResponse?.message ?: "")
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return ResponseState.Fail("")
        }
    }

    override suspend fun getImageSearchResultFlow(
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
            pagingSourceFactory = { PagingSource(kakaoService, localDataSource, query, sort, size) }
        ).flow
    }
}