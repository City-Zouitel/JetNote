package com.example.datastore

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepoMod {

    @Binds
    abstract fun bindRepository(impl: DataStoreRepoImpl): DataStoreRepo
}