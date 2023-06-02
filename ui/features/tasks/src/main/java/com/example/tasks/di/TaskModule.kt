package com.example.tasks.di

import com.example.tasks.mapper.NoteAndTaskMapper
import com.example.tasks.mapper.TaskMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TaskModule {

    @Singleton
    @Provides
    fun provideTaskMapper() = TaskMapper()

    @Singleton
    @Provides
    fun provideNoteAndTaskMapper() = NoteAndTaskMapper()
}