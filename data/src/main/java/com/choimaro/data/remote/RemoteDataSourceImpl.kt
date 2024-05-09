package com.choimaro.data.remote

import com.choimaro.data.service.KakaoService
import com.choimaro.domain.extensions.getFormattedDate
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.ErrorResponse
import com.choimaro.domain.model.SearchListType
import com.choimaro.domain.model.image.ImageModel
import com.google.gson.Gson
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
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
                    val imageModelList = response.body()?.documents?.map { imageDocument ->
                        ImageModel(
                            thumbnailUrl = imageDocument.thumbnailUrl,
                            imageUrl = imageDocument.imageUrl,
                            displaySiteName = imageDocument.displaySiteName,
                            datetime = imageDocument.dateTime?.getFormattedDate() ?: "Unknown date",
                            itemType = SearchListType.IMAGE,
                            docUrl = imageDocument.docUrl,
                            isBookMark = false
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
            return ResponseState.Fail(e.message ?: "")
        }
    }

}