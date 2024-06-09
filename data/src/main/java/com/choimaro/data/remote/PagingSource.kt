package com.choimaro.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.choimaro.data.service.KakaoService
import com.choimaro.data.util.Utils.generateHash
import com.choimaro.domain.extensions.getFormattedDate
import com.choimaro.domain.model.ErrorResponse
import com.choimaro.domain.model.ImageModel
import com.choimaro.domain.model.SearchListType
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject

class PagingSource @Inject constructor(
    private val kakaoService: KakaoService,
    private val retrofit: Retrofit,
    private val query: String,
    private val sort: String,
    private val size: Int
) : PagingSource<Int, ImageModel>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageModel> {
        val currentPage = params.key ?: 1
        return try {
            kakaoService.searchImage(query, sort, currentPage, size).let { response ->
                if (response.isSuccessful) {
                    val imageModelList = response.body()?.documents?.map { imageDocument ->
                        ImageModel(
                            thumbnailUrl = imageDocument.thumbnailUrl ?: "",
                            imageUrl = imageDocument.imageUrl ?: "",
                            displaySiteName = imageDocument.displaySiteName ?: "",
                            datetime = imageDocument.dateTime?.getFormattedDate() ?: "Unknown date",
                            docUrl = imageDocument.docUrl ?: "",
                            isCheckedBookMark = false,
                            id = generateHash(imageDocument.imageUrl + imageDocument.docUrl + SearchListType.IMAGE.name)
                        )
                    } ?: emptyList()
                    LoadResult.Page(
                        data = imageModelList,
                        prevKey = if (currentPage == 1) null else currentPage - 1,
                        nextKey = if (response.body()?.metaData?.isEnd == true) null else currentPage + 1
                    )
                } else {
                    val errorResponse = parseError(response)
                    LoadResult.Error(Exception(errorResponse.message))
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ImageModel>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1) ?: state.closestPageToPosition(
                position
            )?.nextKey?.minus(1)
        }
    }
    private fun parseError(response: Response<*>): ErrorResponse {
        val converter = retrofit.responseBodyConverter<ErrorResponse>(ErrorResponse::class.java, arrayOfNulls(0))
        return response.errorBody()?.let {
            converter.convert(it)
        } ?: ErrorResponse("UnknownError", "An unknown error occurred")
    }
}