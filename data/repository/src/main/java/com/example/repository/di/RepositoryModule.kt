package com.example.repository.di

import com.example.domain.repository.DataRepository
import com.example.domain.repository.LinkRepository
import com.example.repository.repositoryImpl.DataRepositoryImpl
import com.example.repository.repositoryImpl.LinkRepositoryImpl
import com.example.repository.repositoryImpl.NoteRepositoryImpl
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

}