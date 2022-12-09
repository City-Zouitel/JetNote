package com.example.jetnote.di

import  android.content.Context
import androidx.room.Room
import com.example.jetnote.cons.DATABASE_NAME
import com.example.jetnote.db.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseMod {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context:Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        Database::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration()
        .fallbackToDestructiveMigrationOnDowngrade()
        .build()

    @Singleton
    @Provides
    fun provideNoteDao(database: Database) = database.getNoteDao()

    @Singleton
    @Provides
    fun provideLabelDao(database: Database) = database.getLabelDao()

    @Singleton
    @Provides
    fun provideNoteAndLabelDao(database: Database) = database.getNoteAndLabelDao()

    @Singleton
    @Provides
    fun provideEntityDao(database: Database) = database.getEntityDao()

    @Singleton
    @Provides
    fun provideTodoDao(database: Database) = database.getTodoDao()

    @Singleton
    @Provides
    fun provideNoteAndTodoDao(database: Database) = database.getNoteAndTodoDao()

}