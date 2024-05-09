package com.choimaro.domain.image.usecase.db.bookmark

import com.choimaro.domain.entity.BookMarkEntity
import com.choimaro.domain.image.repository.db.BookMarkRepository
import javax.inject.Inject

class GetAllBookMarkUseCase @Inject constructor(
    private val bookMarkRepository: BookMarkRepository
) {
    suspend operator fun invoke(): List<BookMarkEntity> {
        return bookMarkRepository.getAllBookMark()
    }
}