package com.example.note.di

import com.example.links.mapper.LinkMapper
import com.example.note.mapper.DataMapper
import com.example.note.mapper.NoteMapper
import com.example.tags.mapper.TagMapper
import com.example.tasks.mapper.TaskMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NoteModule {

    @Singleton
    @Provides
    fun provideDataMapper() = DataMapper()

    @Singleton
    @Provides
    fun provideNoteMapper(
        dataMapper: DataMapper,
        tagMapper: TagMapper,
        taskMapper: TaskMapper,
        linkMapper: LinkMapper
    ) = NoteMapper(dataMapper, tagMapper, taskMapper, linkMapper)
}