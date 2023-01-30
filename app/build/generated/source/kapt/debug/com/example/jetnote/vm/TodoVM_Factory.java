package com.example.jetnote.vm;

import com.example.domain.reposImpl.TodoRepoImp;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;
import kotlinx.coroutines.CoroutineDispatcher;

@ScopeMetadata
@QualifierMetadata("com.example.jetnote.di.utils.Dispatcher")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes"
})
public final class TodoVM_Factory implements Factory<TodoVM> {
  private final Provider<CoroutineDispatcher> ioDispatcherProvider;

  private final Provider<TodoRepoImp> repoProvider;

  public TodoVM_Factory(Provider<CoroutineDispatcher> ioDispatcherProvider,
      Provider<TodoRepoImp> repoProvider) {
    this.ioDispatcherProvider = ioDispatcherProvider;
    this.repoProvider = repoProvider;
  }

  @Override
  public TodoVM get() {
    return newInstance(ioDispatcherProvider.get(), repoProvider.get());
  }

  public static TodoVM_Factory create(Provider<CoroutineDispatcher> ioDispatcherProvider,
      Provider<TodoRepoImp> repoProvider) {
    return new TodoVM_Factory(ioDispatcherProvider, repoProvider);
  }

  public static TodoVM newInstance(CoroutineDispatcher ioDispatcher, TodoRepoImp repo) {
    return new TodoVM(ioDispatcher, repo);
  }
}
