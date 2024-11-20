package city.zouitel.base.di

import city.zouitel.base.RootManager
import city.zouitel.base.datasourceImpl.RootDataSourceImpl
import city.zouitel.base.mapper.RootMapper
import city.zouitel.repository.datasource.RootDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val baseKoinModule = module {
    factoryOf(::RootMapper)
    single { RootManager(androidContext()) }
    singleOf(::RootDataSourceImpl) bind RootDataSource::class
}