package city.zouitel.media.di

import city.zouitel.media.mapper.MediaMapper
import city.zouitel.media.ui.MediaScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val mediaKoinModule = module {
    factoryOf(::MediaScreenModel)
    factoryOf(::MediaMapper)
}