package com.choimaro.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.choimaro.data.db.entity.ImageBookMarkEntity

@Dao
interface BookMarkDAO {
    @Query("SELECT * FROM book_mark")
    fun getAllBookMark(): List<ImageBookMarkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookMarkEntity: ImageBookMarkEntity): Long

    @Query("DELETE FROM book_mark WHERE id IN (:ids)")
    fun delete(ids: List<String>): Int

    @Query("DELETE FROM book_mark")
    fun deleteAll()
}