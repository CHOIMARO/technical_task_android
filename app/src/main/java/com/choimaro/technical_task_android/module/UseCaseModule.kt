package com.choimaro.technical_task_android.module

import com.choimaro.domain.image.repository.ImageRepository
import com.choimaro.domain.image.usecase.image.GetImageSearchUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetImageSearchUseCase(imageRepository: ImageRepository) = GetImageSearchUseCase(imageRepository)

}