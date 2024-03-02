package city.zouitel.tags.di

import city.zouitel.tags.mapper.NoteAndTagMapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.viewmodel.*
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val tagsKoinModule = module {
    factoryOf(::TagMapper)
    factoryOf(::NoteAndTagMapper)

    factoryOf(::NoteAndTagScreenModel)
    factoryOf(::TagScreenModel)
}