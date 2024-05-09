package com.choimaro.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.choimaro.domain.entity.BookMarkEntity

@Dao
interface BookMarkDAO {
    @Query("SELECT * FROM book_mark")
    fun getAllBookMark(): List<BookMarkEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(bookMarkEntity: BookMarkEntity): Long

    @Query("DELETE FROM book_mark WHERE id=:id")
    fun delete(id: String)

    @Query("DELETE FROM book_mark")
    fun deleteAll()
}