package city.zouitel.systemDesign.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import city.zouitel.systemDesign.DataStoreVM

val datastoreVMKoinModule = module {
    viewModelOf(::DataStoreVM)
}