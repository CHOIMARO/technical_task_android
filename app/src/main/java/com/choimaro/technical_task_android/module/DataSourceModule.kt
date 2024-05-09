package com.choimaro.technical_task_android.module

import com.choimaro.data.db.dao.BookMarkDAO
import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.local.LocalDataSourceImpl
import com.choimaro.data.remote.RemoteDataSource
import com.choimaro.data.remote.RemoteDataSourceImpl
import com.choimaro.data.service.KakaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun provideLocalDataSourceImpl(bookMarkDAO: BookMarkDAO): LocalDataSource {
        return LocalDataSourceImpl(bookMarkDAO)
    }
    @Singleton
    @Provides
    fun provideRemoteDataSourceImpl(localDataSource: LocalDataSource, kakaoService: KakaoService): RemoteDataSource {
        return RemoteDataSourceImpl(localDataSource, kakaoService)
    }
}