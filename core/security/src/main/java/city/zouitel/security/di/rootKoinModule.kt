package city.zouitel.security.di

import city.zouitel.repository.datasource.RootDataSource
import city.zouitel.security.RootChecker
import city.zouitel.security.datasourceImpl.RootDataSourceImpl
import city.zouitel.security.mapper.RootMapper
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val securityKoinModule = module {

    factoryOf(::RootMapper)
    single { RootChecker(androidContext()) }
    singleOf(::RootDataSourceImpl) bind RootDataSource::class
}