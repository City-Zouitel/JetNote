package com.example.local.di

import android.app.Application
import android.content.Context
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
object DatabaseModule {

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
}