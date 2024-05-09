package com.choimaro.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.choimaro.data.db.dao.BookMarkDAO
import com.choimaro.domain.entity.BookMarkEntity

@Database(
    entities = [BookMarkEntity::class],
    version = 1,
    exportSchema = false
)
abstract class DatabaseManager : RoomDatabase() {
    abstract fun bookMarKDao() : BookMarkDAO

    companion object {
        const val DATABASE_NAME = "DatabaseManager.db"
    }
}