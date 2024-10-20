package city.zouitel.note.di

import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.ui.DataScreenModel
import city.zouitel.note.ui.workplace.WorkplaceScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val noteKoinModule = module {

    factoryOf(::DataMapper)
    factoryOf(::WorkplaceScreenModel)
    factory {
        DataScreenModel(get(), get(), get(), get(), get())
    }
}