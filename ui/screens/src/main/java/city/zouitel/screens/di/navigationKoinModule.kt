package city.zouitel.screens.di

import org.koin.dsl.module
import city.zouitel.screens.home_screen.HomeScreenModel
import org.koin.core.module.dsl.factoryOf

val navigationKoinModule = module {
    factoryOf(::HomeScreenModel)
}