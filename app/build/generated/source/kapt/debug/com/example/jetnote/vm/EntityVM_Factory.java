package com.example.jetnote.vm;

import com.example.domain.reposImpl.EntityRepoImp;
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
public final class EntityVM_Factory implements Factory<EntityVM> {
  private final Provider<EntityRepoImp> repoProvider;

  public EntityVM_Factory(Provider<EntityRepoImp> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public EntityVM get() {
    return newInstance(repoProvider.get());
  }

  public static EntityVM_Factory create(Provider<EntityRepoImp> repoProvider) {
    return new EntityVM_Factory(repoProvider);
  }

  public static EntityVM newInstance(EntityRepoImp repo) {
    return new EntityVM(repo);
  }
}
