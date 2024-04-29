package city.zouitel.systemDesign.di

import org.koin.dsl.module
import city.zouitel.systemDesign.DataStoreScreenModel
import org.koin.core.module.dsl.factoryOf

val commonSystemDesignKoinModule = module {
    factoryOf(::DataStoreScreenModel)
}