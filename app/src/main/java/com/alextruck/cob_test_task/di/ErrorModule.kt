package com.alextruck.cob_test_task.di

import com.alextruck.cob_test_task.data.error.mapper.ErrorMapper
import com.alextruck.cob_test_task.data.error.mapper.ErrorMapperSource
import com.alextruck.cob_test_task.usecase.errors.ErrorManager
import com.alextruck.cob_test_task.usecase.errors.ErrorUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorModule {
    @Binds
    @Singleton
    abstract fun provideErrorFactoryImpl(errorManager: ErrorManager): ErrorUseCase

    @Binds
    @Singleton
    abstract fun provideErrorMapper(errorMapper: ErrorMapper): ErrorMapperSource
}