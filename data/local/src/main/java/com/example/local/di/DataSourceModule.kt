package com.example.local.di

import com.example.local.datasourceImpl.*
import com.example.repository.datasource.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindLinkDataSource(data: LinkDataSourceImpl): LinkDataSource

    @Binds
    @Singleton
    fun bindDataDataSource(data: DataDataSourceImpl): DataDataSource

    @Binds
    @Singleton
    fun bindNoteAndLinkDataSource(data: NoteAndLinkDataSourceImpl): NoteAndLinkDataSource

    @Binds
    @Singleton
    fun bindNoteAndTagDataSource(data: NoteAndTagDataSourceImpl): NoteAndTagDataSource

    @Binds
    @Singleton
    fun bindNoteAndTaskDataSource(data: NoteAndTaskDataSourceImpl): NoteAndTaskDataSource

    @Binds
    @Singleton
    fun bindNoteDataSource(data: NoteDataSourceImpl): NoteDataSource

    @Binds
    @Singleton
    fun bindTagDataSource(data: TagDataSourceImpl): TagDataSource

    @Binds
    @Singleton
    fun bindTaskDataSource(data: TaskDataSourceImpl): TaskDataSource

    @Binds
    @Singleton
    fun bindWidgetDataSource(data: WidgetDataSourceImpl): WidgetDataSource
}