package com.example.common_ui

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(
    SingletonComponent::class,
    ViewModelComponent::class
)
object DispatcherMod {

//    @Provides
//    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}