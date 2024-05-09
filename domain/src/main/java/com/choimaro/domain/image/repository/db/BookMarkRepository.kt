package com.choimaro.domain.image.repository.db

import com.choimaro.domain.entity.BookMarkEntity

interface BookMarkRepository {
    suspend fun getAllBookMark(): List<BookMarkEntity>
    suspend fun insertBookMark(bookMarkEntity: BookMarkEntity): Long
    suspend fun deleteBookMark(bookMarkEntity: BookMarkEntity)
    suspend fun deleteAll()
}