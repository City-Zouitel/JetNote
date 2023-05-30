package com.example.local.di

import com.example.local.mapper.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MapperModule {

    @Singleton
    @Provides
    fun provideDataMapper() = DataMapper()

    @Singleton
    @Provides
    fun provideLinkMapper() = LinkMapper()

    @Singleton
    @Provides
    fun provideTagMapper() = TagMapper()

    @Singleton
    @Provides
    fun provideTaskMapper() = TaskMapper()

    @Singleton
    @Provides
    fun provideNoteAndLinkMapper() = NoteAndLinkMapper()

    @Singleton
    @Provides
    fun provideNoteAndTagMapper() = NoteAndTagMapper()

    @Singleton
    @Provides
    fun provideNoteAndTaskMapper() = NoteAndTaskMapper()

    @Singleton
    @Provides
    fun provideNoteMapper(
        dataMapper: DataMapper,
        tagMapper: TagMapper,
        taskMapper: TaskMapper,
        linkMapper: LinkMapper
    ) = NoteMapper(dataMapper, tagMapper, taskMapper, linkMapper)

    @Singleton
    @Provides
    fun provideWidgetMapper(
        dataMapper: DataMapper,
        tagMapper: TagMapper,
        taskMapper: TaskMapper,
        linkMapper: LinkMapper
    ) = WidgetMapper(dataMapper, tagMapper, taskMapper, linkMapper)
}