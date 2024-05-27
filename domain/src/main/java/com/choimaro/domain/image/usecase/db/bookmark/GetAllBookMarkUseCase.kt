package com.choimaro.domain.image.usecase.db.bookmark

import com.choimaro.domain.image.repository.db.BookMarkRepository
import com.choimaro.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBookMarkUseCase @Inject constructor(
    private val bookMarkRepository: BookMarkRepository
) {
    suspend operator fun invoke(): Flow<List<ImageModel>> {
        return bookMarkRepository.getAllBookMark()
    }
}