package city.zouitel.note.di

import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.*
import city.zouitel.note.mapper.NoteMapper
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val noteKoinModule = module {
    factoryOf(::DataMapper)
    factory {
        NoteMapper(get(), get(), get(), get())
    }

    viewModelOf(::DataViewModel)
    viewModelOf(::NoteViewModel)
}