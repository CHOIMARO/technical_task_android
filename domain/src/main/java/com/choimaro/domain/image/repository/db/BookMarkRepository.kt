package com.choimaro.domain.image.repository.db

import com.choimaro.domain.model.ImageModel
import kotlinx.coroutines.flow.Flow

interface BookMarkRepository {
    suspend fun getAllBookMark(): Flow<List<ImageModel>>
    suspend fun insertBookMark(imageModel: ImageModel): Boolean
    suspend fun deleteBookMark(ids: List<String>): Boolean
    suspend fun deleteAll()
}