package com.example.jetnote.di

import com.example.jetnote.di.utils.Dispatcher
import com.example.jetnote.di.utils.Dispatchers.*
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

    @Dispatcher(IO)
    @Provides
    fun ioDispatcher(): CoroutineDispatcher = Dispatchers.IO
}