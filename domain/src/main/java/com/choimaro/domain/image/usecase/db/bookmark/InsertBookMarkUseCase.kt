package com.choimaro.domain.image.usecase.db.bookmark

import com.choimaro.domain.image.repository.db.BookMarkRepository
import com.choimaro.domain.model.ImageModel
import javax.inject.Inject

class InsertBookMarkUseCase @Inject constructor(
    private val bookMarkRepository: BookMarkRepository
) {
    suspend operator fun invoke(imageModel: ImageModel): Boolean {
        return bookMarkRepository.insertBookMark(imageModel)
    }
}
