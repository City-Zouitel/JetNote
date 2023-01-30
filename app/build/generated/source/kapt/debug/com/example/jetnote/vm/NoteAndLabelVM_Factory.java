package com.example.jetnote.vm;

import com.example.domain.reposImpl.NoteAndLabelRepoImp;
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
public final class NoteAndLabelVM_Factory implements Factory<NoteAndLabelVM> {
  private final Provider<NoteAndLabelRepoImp> repoProvider;

  public NoteAndLabelVM_Factory(Provider<NoteAndLabelRepoImp> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public NoteAndLabelVM get() {
    return newInstance(repoProvider.get());
  }

  public static NoteAndLabelVM_Factory create(Provider<NoteAndLabelRepoImp> repoProvider) {
    return new NoteAndLabelVM_Factory(repoProvider);
  }

  public static NoteAndLabelVM newInstance(NoteAndLabelRepoImp repo) {
    return new NoteAndLabelVM(repo);
  }
}
