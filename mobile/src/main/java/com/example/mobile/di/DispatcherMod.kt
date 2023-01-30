package com.example.mobile.di

import com.example.domain.utils.Dispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(
    SingletonComponent::class,
    ViewModelComponent::class
)
object DispatcherMod {

//    @Provides
//    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}