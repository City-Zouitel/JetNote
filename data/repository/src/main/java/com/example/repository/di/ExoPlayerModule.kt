package com.example.repository.di

import com.example.domain.exoplayer.ExoRepositoryImpl
import com.google.android.exoplayer2.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExoPlayerModule {

    @Singleton
    @Provides
    fun provideExoPlayer(exo: ExoPlayer) = ExoRepositoryImpl(exo)
}