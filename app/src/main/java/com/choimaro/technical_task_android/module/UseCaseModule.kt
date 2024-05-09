package com.choimaro.technical_task_android.module

import com.choimaro.domain.image.repository.ImageRepository
import com.choimaro.domain.image.repository.db.BookMarkRepository
import com.choimaro.domain.image.usecase.db.bookmark.DeleteBookMarkUseCase
import com.choimaro.domain.image.usecase.db.bookmark.GetAllBookMarkUseCase
import com.choimaro.domain.image.usecase.db.bookmark.InsertBookMarkUseCase
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

    @Singleton
    @Provides
    fun provideDeleteBookMarkUseCase(bookMarkRepository: BookMarkRepository) = DeleteBookMarkUseCase(bookMarkRepository)
    @Singleton
    @Provides
    fun provideGetAllBookMarkUseCase(bookMarkRepository: BookMarkRepository) = GetAllBookMarkUseCase(bookMarkRepository)
    @Singleton
    @Provides
    fun provideInsertBookMarkUseCase(bookMarkRepository: BookMarkRepository) = InsertBookMarkUseCase(bookMarkRepository)

}