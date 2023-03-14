package com.example.glance.di

import com.example.glance.data.NoteGlanceUpdater
import com.example.glance.mapper.NoteMapper
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val glanceModule = module {

    singleOf(::NoteGlanceUpdater)

    singleOf(::NoteMapper)
}

