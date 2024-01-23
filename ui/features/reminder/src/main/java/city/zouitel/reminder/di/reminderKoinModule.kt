package city.zouitel.reminder.di

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import city.zouitel.reminder.viewmodel.ReminderVM

val reminderKoinModule = module {
    viewModelOf(::ReminderVM)
}