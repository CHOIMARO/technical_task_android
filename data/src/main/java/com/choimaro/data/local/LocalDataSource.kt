package com.choimaro.data.local

import com.choimaro.data.db.entity.ImageBookMarkEntity

interface LocalDataSource {
    suspend fun getAllBookMark(): List<ImageBookMarkEntity>
    suspend fun insertBookMark(imageBookMarkEntity: ImageBookMarkEntity): Boolean
    suspend fun deleteBookMark(ids: List<String>): Boolean
    suspend fun deleteAll()
}