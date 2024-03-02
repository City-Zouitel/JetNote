package city.zouitel.navigation.di

import org.koin.dsl.module
import city.zouitel.navigation.home_screen.HomeScreenModel
import org.koin.core.module.dsl.factoryOf

val navigationKoinModule = module {
    factoryOf(::HomeScreenModel)
}