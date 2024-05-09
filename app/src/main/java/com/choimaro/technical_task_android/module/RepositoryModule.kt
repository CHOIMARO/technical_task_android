package com.choimaro.technical_task_android.module

import com.choimaro.data.image.repository.ImageRepositoryImpl
import com.choimaro.data.module.CoroutinesQualifiers
import com.choimaro.data.remote.RemoteDataSource
import com.choimaro.domain.image.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideImageRepository(
        remoteDataSource: RemoteDataSource,
        @CoroutinesQualifiers.IoDispatcher coroutinesDisPatcher: CoroutineDispatcher
    ): ImageRepository {
        return ImageRepositoryImpl(remoteDataSource, coroutinesDisPatcher)
    }
}