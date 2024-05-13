package com.choimaro.data.remote

import com.choimaro.data.R
import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.service.KakaoService
import com.choimaro.domain.extensions.getFormattedDate
import com.choimaro.domain.ResponseState
import com.choimaro.domain.model.ErrorResponse
import com.choimaro.domain.model.SearchListType
import com.choimaro.domain.model.image.ImageModel
import com.google.gson.Gson
import java.net.UnknownHostException
import java.security.MessageDigest
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
                            thumbnailUrl = imageDocument.thumbnailUrl,
                            imageUrl = imageDocument.imageUrl,
                            displaySiteName = imageDocument.displaySiteName,
                            datetime = imageDocument.dateTime?.getFormattedDate() ?: "Unknown date",
                            itemType = SearchListType.IMAGE,
                            docUrl = imageDocument.docUrl,
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
            return ResponseState.Fail("접속상태가 원활하지 않습니다. 잠시 후 다시 시도해 주세요.")
        }
    }
    private fun generateHash(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)
        return digest.fold("") { str, it -> str + "%02x".format(it) }
    }
}