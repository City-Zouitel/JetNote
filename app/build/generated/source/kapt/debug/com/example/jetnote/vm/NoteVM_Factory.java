package com.example.jetnote.vm;

import com.example.domain.reposImpl.NoteRepoImp;
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
public final class NoteVM_Factory implements Factory<NoteVM> {
  private final Provider<NoteRepoImp> repoProvider;

  public NoteVM_Factory(Provider<NoteRepoImp> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public NoteVM get() {
    return newInstance(repoProvider.get());
  }

  public static NoteVM_Factory create(Provider<NoteRepoImp> repoProvider) {
    return new NoteVM_Factory(repoProvider);
  }

  public static NoteVM newInstance(NoteRepoImp repo) {
    return new NoteVM(repo);
  }
}
