package com.choimaro.data.db.repository

import com.choimaro.data.db.entity.toBookMarkEntity
import com.choimaro.data.db.entity.toDomainModel
import com.choimaro.data.local.LocalDataSource
import com.choimaro.domain.image.repository.db.BookMarkRepository
import com.choimaro.domain.model.ImageModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookMarkRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val ioDispatcher: CoroutineDispatcher
) : BookMarkRepository {
    override suspend fun getAllBookMark(): List<ImageModel> = withContext(ioDispatcher) {
        localDataSource.getAllBookMark().map { bookMarkItem ->
            bookMarkItem.toDomainModel()
        }
    }

    override suspend fun insertBookMark(imageModel: ImageModel): Boolean =
        withContext(ioDispatcher) {
            localDataSource.insertBookMark(imageModel.toBookMarkEntity())
        }

    override suspend fun deleteBookMark(ids: List<String>): Boolean = withContext(ioDispatcher) {
        localDataSource.deleteBookMark(ids)
    }

    override suspend fun deleteAll() = withContext(ioDispatcher) {
        localDataSource.deleteAll()
    }
}