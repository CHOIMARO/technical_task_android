package com.choimaro.technical_task_android.module

import com.choimaro.data.db.repository.BookMarkRepositoryImpl
import com.choimaro.data.image.repository.ImageRepositoryImpl
import com.choimaro.data.local.LocalDataSource
import com.choimaro.data.module.CoroutinesQualifiers
import com.choimaro.data.remote.RemoteDataSource
import com.choimaro.domain.image.repository.ImageRepository
import com.choimaro.domain.image.repository.db.BookMarkRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.annotation.Signed
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideImageRepository(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        @CoroutinesQualifiers.IoDispatcher coroutinesDisPatcher: CoroutineDispatcher
    ): ImageRepository {
        return ImageRepositoryImpl(localDataSource, remoteDataSource, coroutinesDisPatcher)
    }
    @Singleton
    @Provides
    fun provideBookMarkRepository(
        localDataSource: LocalDataSource,
        @CoroutinesQualifiers.IoDispatcher coroutinesDisPatcher: CoroutineDispatcher
    ): BookMarkRepository {
        return BookMarkRepositoryImpl(localDataSource, coroutinesDisPatcher)
    }
}