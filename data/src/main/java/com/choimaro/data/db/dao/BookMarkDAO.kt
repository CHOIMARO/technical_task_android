package com.choimaro.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.choimaro.domain.entity.BookMarkEntity

@Dao
interface BookMarkDAO {
    @Query("SELECT * FROM book_mark")
    fun getAllBookMark(): List<BookMarkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookMarkEntity: BookMarkEntity): Long

    @Query("DELETE FROM book_mark WHERE id IN (:ids)")
    fun delete(ids: List<String>): Int

    @Query("DELETE FROM book_mark")
    fun deleteAll()
}