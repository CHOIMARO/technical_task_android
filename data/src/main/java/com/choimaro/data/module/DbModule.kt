package com.choimaro.data.module

import android.content.Context
import androidx.room.Room
import com.choimaro.data.db.DatabaseManager
import com.choimaro.data.db.dao.ImageBookMarkDAO
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DbModule {
    @Singleton
    @Provides
    fun provideDatabaseManager(@ApplicationContext context: Context): DatabaseManager {
        return Room
            .databaseBuilder(
                context,
                DatabaseManager::class.java,
                DatabaseManager.DATABASE_NAME
            ).build()
    }
    @Singleton
    @Provides
    fun provideBookMarkDao(databaseManager: DatabaseManager): ImageBookMarkDAO {
        return databaseManager.imageBookMarKDao()
    }
}