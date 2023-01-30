package com.example.jetnote.vm;

import com.example.domain.reposImpl.ExoRepoImp;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class MediaPlayerVM_Factory implements Factory<MediaPlayerVM> {
  private final Provider<ExoRepoImp> exoBuilderProvider;

  public MediaPlayerVM_Factory(Provider<ExoRepoImp> exoBuilderProvider) {
    this.exoBuilderProvider = exoBuilderProvider;
  }

  @Override
  public MediaPlayerVM get() {
    return newInstance(exoBuilderProvider.get());
  }

  public static MediaPlayerVM_Factory create(Provider<ExoRepoImp> exoBuilderProvider) {
    return new MediaPlayerVM_Factory(exoBuilderProvider);
  }

  public static MediaPlayerVM newInstance(ExoRepoImp exoBuilder) {
    return new MediaPlayerVM(exoBuilder);
  }
}
