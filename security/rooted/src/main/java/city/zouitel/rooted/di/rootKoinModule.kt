package city.zouitel.rooted.di

import city.zouitel.rooted.RootScreenModel
import city.zouitel.rooted.mapper.RootMapper
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val rootedKoinModule = module {

    factory { RootScreenModel(get(), get()) }
    factoryOf(::RootMapper)
}