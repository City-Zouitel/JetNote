package com.example.local.di

import com.example.local.datasourceImpl.LinkDataSourceImpl
import com.example.repository.datasource.LinkDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {

    @Binds
    @Singleton
    fun bindLinkDataSource(data: LinkDataSourceImpl): LinkDataSource
}