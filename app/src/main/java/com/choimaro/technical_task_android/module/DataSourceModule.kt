package com.choimaro.technical_task_android.module

import com.choimaro.data.db.dao.ImageBookMarkDAO
import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.remote.RemoteDataSource
import com.choimaro.data.service.KakaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {
    @Singleton
    @Provides
    fun provideLocalDataSource(bookMarkDAO: ImageBookMarkDAO) = LocalDataSource(bookMarkDAO)
    @Singleton
    @Provides
    fun provideRemoteDataSource(kakaoService: KakaoService, retrofit: Retrofit) = RemoteDataSource(kakaoService, retrofit)
}