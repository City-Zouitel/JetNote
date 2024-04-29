package city.zouitel.quicknote.di

import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import city.zouitel.quicknote.mapper.QuickDataMapper
import city.zouitel.quicknote.ui.QuickDataScreenModel

val quickNoteKoinModule = module {
    factoryOf(::QuickDataMapper)
    factoryOf(::QuickDataScreenModel)
}