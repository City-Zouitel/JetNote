package city.zouitel.quicknote.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import city.zouitel.quicknote.mapper.QuickDataMapper
import city.zouitel.quicknote.ui.QuickDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf

val quickNoteKoinModule = module {
    factoryOf(::QuickDataMapper)
    viewModelOf(::QuickDataViewModel)
}