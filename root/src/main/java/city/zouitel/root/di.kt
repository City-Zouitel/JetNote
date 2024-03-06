package city.zouitel.root

import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val rootKoinModule = module {

    single { Root(androidContext()) }
    singleOf(::RootRepoImpl) bind RootRepo::class
    singleOf(::RootSourceImpl) bind RootSource::class

    factoryOf(::RootUseCase)

    viewModel { RootViewModel(get()) }
}