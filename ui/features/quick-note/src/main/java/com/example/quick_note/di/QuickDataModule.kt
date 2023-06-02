package com.example.quick_note.di

import com.example.quick_note.mapper.QuickDataMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuickDataModule {

    @Singleton
    @Provides
    fun provideQuickDataMapper() = QuickDataMapper()
}