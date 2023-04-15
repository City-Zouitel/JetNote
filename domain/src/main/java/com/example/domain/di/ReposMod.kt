package com.example.domain.di

import com.example.domain.repos.EntityRepo
import com.example.domain.reposImpl.EntityRepoImp
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReposMod {

    @Binds
    abstract fun entityBinds(impl: EntityRepoImp): EntityRepo
}