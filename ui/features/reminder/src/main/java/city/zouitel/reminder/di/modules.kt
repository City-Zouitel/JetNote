package city.zouitel.reminder.di

import cafe.adriel.voyager.core.registry.screenModule
import city.zouitel.domain.provider.SharedScreen
import city.zouitel.reminder.mapper.ReminderMapper
import city.zouitel.reminder.ui.ReminderScreen
import city.zouitel.reminder.ui.ReminderScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val reminderDIModule = module {
    factoryOf(::ReminderMapper)
    factoryOf(::ReminderScreenModel)
}

val reminderScreenModule = screenModule {
    register<SharedScreen.Reminder> {
        ReminderScreen(it.uid, it.title, it.message)
    }
}