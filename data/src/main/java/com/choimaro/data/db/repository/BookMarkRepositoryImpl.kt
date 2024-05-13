package com.choimaro.data.db.repository

import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.module.CoroutinesQualifiers
import com.choimaro.domain.entity.BookMarkEntity
import com.choimaro.domain.image.repository.db.BookMarkRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookMarkRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
): BookMarkRepository {
    override suspend fun getAllBookMark(): List<BookMarkEntity> = withContext(ioDispatcher) {
        localDataSource.getAllBookMark()
    }

    override suspend fun insertBookMark(bookMarkEntity: BookMarkEntity): Boolean = withContext(ioDispatcher) {
        localDataSource.insertBookMark(bookMarkEntity)
    }

    override suspend fun deleteBookMark(ids: List<String>): Boolean = withContext(ioDispatcher) {
        localDataSource.deleteBookMark(ids)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        localDataSource.deleteAll()
    }
}