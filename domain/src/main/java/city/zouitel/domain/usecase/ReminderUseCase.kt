package city.zouitel.domain.usecase

import city.zouitel.domain.model.Reminder
import city.zouitel.domain.repository.ReminderRepo

sealed class ReminderUseCase {

    data class ObserveAllReminders(val repository: ReminderRepo): ReminderUseCase() {
        operator fun invoke() = repository.observeAllReminders
    }

    data class ObserveReminderById(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(id: Long) = repository.observeRemindersById(id)
    }

    data class InsertReminder(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(reminder: Reminder) = repository.insertReminder(reminder)
    }

    data class UpdateReminder(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(reminder: Reminder) = repository.updateReminder(reminder)
    }

    data class DeleteReminderById(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(id: Long) = repository.deleteReminderById(id)
    }

    data class DeleteAllReminders(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke() = repository.deleteAllReminders()
    }
}