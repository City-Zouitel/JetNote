package city.zouitel.note.di

import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.*
import city.zouitel.note.mapper.NoteMapper
import city.zouitel.systemDesign.Cons
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.io.File

val noteKoinModule = module {

    factoryOf(::DataMapper)
    factory {
        NoteMapper(get(), get(), get(), get())
    }

    viewModelOf(::DataViewModel)
    viewModelOf(::NoteViewModel)
}