package city.zouitel.tags.di

import city.zouitel.tags.mapper.NoteAndTagMapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val tagsKoinModule = module {
    factoryOf(::TagMapper)
    factoryOf(::NoteAndTagMapper)

    factoryOf(::NoteAndTagScreenModel)
    factoryOf(::TagScreenModel)
}