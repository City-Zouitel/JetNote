package city.zouitel.note.di

import city.zouitel.note.mapper.DataMapper
import city.zouitel.note.*
import city.zouitel.note.mapper.NoteMapper
import city.zouitel.systemDesign.Cons.IMG_DIR
import org.koin.core.module.dsl.factoryOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val noteKoinModule = module {

    factoryOf(::DataMapper)
    factory {
        NoteMapper(get(), get(), get(), get())
    }

    factory {
        DataScreenModel(get(), get(), get(), get(), get(), get(named(IMG_DIR)))
    }
}