package com.choimaro.data.local

import com.choimaro.data.db.dao.BookMarkDAO
import com.choimaro.data.db.entity.ImageBookMarkEntity
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val bookMarkDAO: BookMarkDAO
): LocalDataSource {
    override suspend fun getAllBookMark(): List<ImageBookMarkEntity> {
        return bookMarkDAO.getAllBookMark()
    }

    override suspend fun insertBookMark(imageBookMarkEntity: ImageBookMarkEntity): Boolean {
        val insertCount = bookMarkDAO.insert(imageBookMarkEntity)
        return insertCount > 0
    }

    override suspend fun deleteBookMark(ids: List<String>): Boolean {
        val deletedCount = bookMarkDAO.delete(ids)
        return deletedCount == ids.size
    }

    override suspend fun deleteAll() {
        return bookMarkDAO.deleteAll()
    }
}