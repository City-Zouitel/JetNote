package city.zouitel.media.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import city.zouitel.media.mapper.*
import city.zouitel.media.ui.*

val mediaKoinModule = module {

    factoryOf(::MediaScreenModel)
    factoryOf(::NoteAndMediaScreenModel)
    factoryOf(::MediaMapper)
    factoryOf(::NoteAndMediaMapper)
}