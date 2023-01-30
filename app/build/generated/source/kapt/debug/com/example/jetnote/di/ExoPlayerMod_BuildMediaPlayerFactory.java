package com.example.jetnote.di;

import android.content.Context;
import com.google.android.exoplayer2.ExoPlayer;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class ExoPlayerMod_BuildMediaPlayerFactory implements Factory<ExoPlayer> {
  private final Provider<Context> contextProvider;

  public ExoPlayerMod_BuildMediaPlayerFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public ExoPlayer get() {
    return buildMediaPlayer(contextProvider.get());
  }

  public static ExoPlayerMod_BuildMediaPlayerFactory create(Provider<Context> contextProvider) {
    return new ExoPlayerMod_BuildMediaPlayerFactory(contextProvider);
  }

  public static ExoPlayer buildMediaPlayer(Context context) {
    return Preconditions.checkNotNullFromProvides(ExoPlayerMod.INSTANCE.buildMediaPlayer(context));
  }
}
