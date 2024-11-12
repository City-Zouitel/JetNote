package city.zouitel.reminder.di

import city.zouitel.reminder.mapper.ReminderMapper
import city.zouitel.reminder.ui.ReminderScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val reminderKoinModule = module {
    factoryOf(::ReminderMapper)
    factoryOf(::ReminderScreenModel)
}