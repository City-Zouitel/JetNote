package city.zouitel.connection.di

import city.zouitel.connection.datasourceImpl.ConnectionDataSourceImpl
import city.zouitel.repository.datasource.ConnectionDataSource
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val connectionDIModule = module {
    singleOf(::ConnectionDataSourceImpl) bind ConnectionDataSource::class

    single {
        ConnectionDataSourceImpl(androidContext())
    }
}