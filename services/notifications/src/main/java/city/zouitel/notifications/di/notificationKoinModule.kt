package city.zouitel.notifications.di

import city.zouitel.notifications.viewmodel.AlarmManagerScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val notificationKoinModule = module {

    factoryOf(::AlarmManagerScreenModel)
}