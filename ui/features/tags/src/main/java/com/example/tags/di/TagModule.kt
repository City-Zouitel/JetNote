package com.example.tags.di

import com.example.tags.mapper.NoteAndTagMapper
import com.example.tags.mapper.TagMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TagModule {

    @Singleton
    @Provides
    fun provideTagMapper() = TagMapper()

    @Singleton
    @Provides
    fun provideNoteAndTagMapper() = NoteAndTagMapper()
}