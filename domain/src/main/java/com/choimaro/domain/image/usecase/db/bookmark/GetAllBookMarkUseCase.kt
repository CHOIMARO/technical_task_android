package com.choimaro.domain.image.usecase.db.bookmark

import com.choimaro.domain.image.repository.db.BookMarkRepository
import com.choimaro.domain.model.ImageModel
import javax.inject.Inject

class GetAllBookMarkUseCase @Inject constructor(
    private val bookMarkRepository: BookMarkRepository
) {
    suspend operator fun invoke(): List<ImageModel> {
        return bookMarkRepository.getAllBookMark()
    }
}