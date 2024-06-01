package com.choimaro.data.local

import com.choimaro.data.db.dao.ImageBookMarkDAO
import com.choimaro.data.db.entity.ImageBookMarkEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val bookMarkDAO: ImageBookMarkDAO
) {
    fun getAllImageBookMark(): Flow<List<ImageBookMarkEntity>> {
        return bookMarkDAO.getAllBookMark()
    }

    suspend fun insertBookMark(imageBookMarkEntity: ImageBookMarkEntity): Boolean {
        val insertCount = bookMarkDAO.insert(imageBookMarkEntity)
        return insertCount > 0
    }

    suspend fun deleteBookMark(ids: List<String>): Boolean {
        val deletedCount = bookMarkDAO.delete(ids)
        return deletedCount == ids.size
    }

    suspend fun deleteAll() {
        return bookMarkDAO.deleteAll()
    }
}