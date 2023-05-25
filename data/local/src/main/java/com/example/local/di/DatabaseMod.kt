package com.example.local.di

import android.app.Application
import  android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.local.utils.Constants.DATABASE_NAME
import com.example.local.Database
import com.example.local.Encryption
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import net.sqlcipher.database.SupportFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseMod {

    @Provides
    @Singleton
    fun provideEncryption(application: Application) = Encryption(application)

    @RequiresApi(Build.VERSION_CODES.O)
    @Provides
    @Singleton
    fun provideSupportEncryption(encryption: Encryption) =
        SupportFactory(encryption.getCrypticPass())

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context,
        supportFactory: SupportFactory
    ) = Room.databaseBuilder(
        context.applicationContext,
        Database::class.java,
        DATABASE_NAME
    ).openHelperFactory(supportFactory)
        .fallbackToDestructiveMigration()
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
    fun provideWidgetEntityDao(database: Database) = database.getWidgetEntityDao()

    @Singleton
    @Provides
    fun provideTodoDao(database: Database) = database.getTodoDao()

    @Singleton
    @Provides
    fun provideNoteAndTodoDao(database: Database) = database.getNoteAndTodoDao()

    @Singleton
    @Provides
    fun provideLinkDao(database: Database) = database.getLinkDao()

    @Singleton
    @Provides
    fun provideNoteAndLinkDao(database: Database) = database.getNoteAndLink()

}