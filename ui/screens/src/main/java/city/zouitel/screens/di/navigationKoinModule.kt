package city.zouitel.screens.di

import org.koin.dsl.module
import city.zouitel.screens.main_screen.MainScreenModel
import org.koin.core.module.dsl.factoryOf

val navigationKoinModule = module {
    factoryOf(::MainScreenModel)
}