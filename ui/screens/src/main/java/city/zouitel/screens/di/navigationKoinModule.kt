package city.zouitel.screens.di

import city.zouitel.assistant.di.assistantKoinModule
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.mapper.NoteMapper
import city.zouitel.screens.navigation_drawer.NavigationDrawerScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val screensKoinModule = module {
    factoryOf(::MainScreenModel)
    factoryOf(::NoteMapper)
    factoryOf(::NavigationDrawerScreenModel)

    includes(assistantKoinModule)
}