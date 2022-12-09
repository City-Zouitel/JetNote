package com.example.jetnote.di

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ExoPlayerMod {

    @Singleton
    @Provides
    fun buildMediaPlayer(
        @ApplicationContext context: Context,
    ) = ExoPlayer.Builder(context).build().apply {
        setHandleAudioBecomingNoisy(true)
    }
}


