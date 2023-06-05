package com.example.repository.di

import com.example.domain.repository.*
import com.example.repository.model.NoteAndTag
import com.example.repository.repositoryImpl.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun bindLinkRepository(data: LinkRepositoryImpl): LinkRepository

    @Binds
    @Singleton
    fun bindDataRepository(data: DataRepositoryImpl): DataRepository

    @Binds
    @Singleton
    fun bindNoteAndLinkRepository(data: NoteAndLinkRepositoryImpl): NoteAndLinkRepository

    @Binds
    @Singleton
    fun bindNoteAndTagRepository(data: NoteAndTagRepositoryImpl): NoteAndTagRepository

    @Binds
    @Singleton
    fun bindNoteAndTaskRepository(data: NoteAndTaskRepositoryImpl): NoteAndTaskRepository

    @Binds
    @Singleton
    fun bindNoteRepository(data: NoteRepositoryImpl): NoteRepository

    @Binds
    @Singleton
    fun bindTagRepository(data: TagRepositoryImpl): TagRepository

    @Binds
    @Singleton
    fun bindTaskRepository(data: TaskRepositoryImpl): TaskRepository

    @Binds
    @Singleton
    fun bindWidgetRepository(data: WidgetRepositoryImpl): WidgetRepository
}