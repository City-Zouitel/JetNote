package com.example.jetnote.vm;

import com.example.domain.reposImpl.NoteAndTodoRepoImp;
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
public final class NoteAndTodoVM_Factory implements Factory<NoteAndTodoVM> {
  private final Provider<NoteAndTodoRepoImp> repoProvider;

  public NoteAndTodoVM_Factory(Provider<NoteAndTodoRepoImp> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public NoteAndTodoVM get() {
    return newInstance(repoProvider.get());
  }

  public static NoteAndTodoVM_Factory create(Provider<NoteAndTodoRepoImp> repoProvider) {
    return new NoteAndTodoVM_Factory(repoProvider);
  }

  public static NoteAndTodoVM newInstance(NoteAndTodoRepoImp repo) {
    return new NoteAndTodoVM(repo);
  }
}
