package com.example.links.di

import android.content.Context
import com.example.links.mapper.LinkMapper
import com.example.links.mapper.NoteAndLinkMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LinkModule {

    @Singleton
    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

    @Provides
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @Provides
    fun provideLinkMapper() = LinkMapper()

    @Singleton
    @Provides
    fun provideNoteAndLinkMapper() = NoteAndLinkMapper()
}