package city.zouitel.tags.di

import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.tags.mapper.NoteAndTagMapper
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.ui.NoteAndTagScreenModel
import city.zouitel.tags.ui.TagScreenModel
import city.zouitel.tags.ui.TagsScreen
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val tagsKoinModule = module {
    factoryOf(::TagMapper)
    factoryOf(::NoteAndTagMapper)

    factoryOf(::NoteAndTagScreenModel)
    factoryOf(::TagScreenModel)
}

val tagsScreenModule = screenModule {
    register<SharedScreen.Tags> {
        TagsScreen(it.uid)
    }
}