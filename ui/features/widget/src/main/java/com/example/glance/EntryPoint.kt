package com.example.glance

import com.example.glance.ui.WidgetViewModel
import dagger.hilt.InstallIn
import dagger.hilt.EntryPoint
import dagger.hilt.components.SingletonComponent


@EntryPoint
@InstallIn(SingletonComponent::class)
interface EntryPoint {

    fun vm(): WidgetViewModel
}