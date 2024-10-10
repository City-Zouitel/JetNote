package city.zouitel.security.di

import city.zouitel.repository.datasource.RootDataSource
import city.zouitel.security.RootManager
import city.zouitel.security.datasourceImpl.RootDataSourceImpl
import city.zouitel.security.mapper.RootMapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val rootCheckerKoinModule = module {

    factoryOf(::RootMapper)
    single { RootManager(androidContext()) }
    singleOf(::RootDataSourceImpl) bind RootDataSource::class
}