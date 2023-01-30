package com.example.jetnote.vm;

import com.example.domain.reposImpl.LabelRepoImp;
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
public final class LabelVM_Factory implements Factory<LabelVM> {
  private final Provider<LabelRepoImp> repoProvider;

  public LabelVM_Factory(Provider<LabelRepoImp> repoProvider) {
    this.repoProvider = repoProvider;
  }

  @Override
  public LabelVM get() {
    return newInstance(repoProvider.get());
  }

  public static LabelVM_Factory create(Provider<LabelRepoImp> repoProvider) {
    return new LabelVM_Factory(repoProvider);
  }

  public static LabelVM newInstance(LabelRepoImp repo) {
    return new LabelVM(repo);
  }
}
