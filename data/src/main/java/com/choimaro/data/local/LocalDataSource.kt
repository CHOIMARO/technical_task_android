package com.choimaro.data.local

import com.choimaro.domain.entity.BookMarkEntity

interface LocalDataSource {
    suspend fun getAllBookMark(): List<BookMarkEntity>
    suspend fun insertBookMark(bookMarkEntity: BookMarkEntity): Long
    suspend fun deleteBookMark(bookMarkEntity: BookMarkEntity)
    suspend fun deleteAll()
}