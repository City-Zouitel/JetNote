package city.zouitel.root.di

import city.zouitel.root.mapper.RootMapper
import city.zouitel.root.RootViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val rootKoinModule = module {

    viewModel { RootViewModel(get(), get()) }
    factoryOf(::RootMapper)
}