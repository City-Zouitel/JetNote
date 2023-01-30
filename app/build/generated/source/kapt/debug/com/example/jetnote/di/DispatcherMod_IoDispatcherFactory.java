package com.example.jetnote.di;

import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
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
public final class DispatcherMod_IoDispatcherFactory implements Factory<CoroutineDispatcher> {
  @Override
  public CoroutineDispatcher get() {
    return ioDispatcher();
  }

  public static DispatcherMod_IoDispatcherFactory create() {
    return InstanceHolder.INSTANCE;
  }

  public static CoroutineDispatcher ioDispatcher() {
    return Preconditions.checkNotNullFromProvides(DispatcherMod.INSTANCE.ioDispatcher());
  }

  private static final class InstanceHolder {
    private static final DispatcherMod_IoDispatcherFactory INSTANCE = new DispatcherMod_IoDispatcherFactory();
  }
}
