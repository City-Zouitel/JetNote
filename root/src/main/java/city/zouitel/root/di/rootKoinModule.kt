package city.zouitel.root.di

import city.zouitel.root.mapper.RootMapper
import city.zouitel.root.RootScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val rootKoinModule = module {

    factory { RootScreenModel(get(), get()) }
    factoryOf(::RootMapper)
}