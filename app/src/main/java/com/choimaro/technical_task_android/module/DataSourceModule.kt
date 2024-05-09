package com.choimaro.technical_task_android.module

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
    fun provideRemoteDataSourceImpl(kakaoService: KakaoService): RemoteDataSource {
        return RemoteDataSourceImpl(kakaoService)
    }
}