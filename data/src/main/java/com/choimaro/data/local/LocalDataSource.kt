package com.choimaro.data.local

import com.choimaro.domain.entity.BookMarkEntity

interface LocalDataSource {
    suspend fun getAllBookMark(): List<BookMarkEntity>
    suspend fun insertBookMark(bookMarkEntity: BookMarkEntity): Boolean
    suspend fun deleteBookMark(ids: List<String>): Boolean
    suspend fun deleteAll()
}