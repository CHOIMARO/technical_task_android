package com.choimaro.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.choimaro.data.db.entity.ImageBookMarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageBookMarkDAO {
    @Query("SELECT * FROM book_mark")
    fun getAllBookMark(): Flow<List<ImageBookMarkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookMarkEntity: ImageBookMarkEntity): Long

    @Query("DELETE FROM book_mark WHERE id IN (:ids)")
    suspend fun delete(ids: List<String>): Int

    @Query("DELETE FROM book_mark")
    suspend fun deleteAll()
}