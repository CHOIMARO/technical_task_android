package com.choimaro.domain.image.usecase.image

import androidx.paging.PagingData
import com.choimaro.domain.image.repository.ImageRepository
import com.choimaro.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetImageSearchFlowUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(query: String, sort: String, page: Int, size: Int): Flow<PagingData<ImageModel>> {
        return imageRepository.getImageSearchResultFlow(query, sort, page, size)
    }
}