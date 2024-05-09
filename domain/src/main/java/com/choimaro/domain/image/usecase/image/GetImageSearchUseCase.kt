package com.choimaro.domain.image.usecase.image

import com.choimaro.domain.ResponseState
import com.choimaro.domain.image.repository.ImageRepository
import javax.inject.Inject

class GetImageSearchUseCase @Inject constructor(
    private val imageRepository: ImageRepository
) {
    suspend operator fun invoke(query: String, sort: String, page: Int, size: Int): ResponseState {
        return imageRepository.getImageSearchResult(query, sort, page, size)
    }
}