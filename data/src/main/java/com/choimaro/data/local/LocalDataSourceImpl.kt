package com.choimaro.data.local

import com.choimaro.data.db.dao.BookMarkDAO
import com.choimaro.data.module.CoroutinesQualifiers
import com.choimaro.domain.entity.BookMarkEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val bookMarkDAO: BookMarkDAO
): LocalDataSource {
    override suspend fun getAllBookMark(): List<BookMarkEntity> {
        return bookMarkDAO.getAllBookMark()
    }

    override suspend fun insertBookMark(bookMarkEntity: BookMarkEntity): Long {
        return bookMarkDAO.insert(bookMarkEntity)
    }

    override suspend fun deleteBookMark(bookMarkEntity: BookMarkEntity) {
        return bookMarkDAO.delete(bookMarkEntity.id!!)
    }

    override suspend fun deleteAll() {
        return bookMarkDAO.deleteAll()
    }
}