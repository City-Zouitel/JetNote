package city.zouitel.domain.usecase

import city.zouitel.domain.model.Reminder
import city.zouitel.domain.repository.ReminderRepo

sealed class ReminderUseCase {

    data class ObserveById(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(uid: String) = repository.observeById(uid)
    }

    data class Insert(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(reminder: Reminder) = repository.insert(reminder)
    }

    data class Update(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(reminder: Reminder) = repository.update(reminder)
    }

    data class DeleteReminder(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(id: Long) = repository.delete(id)
    }

    data class DeleteAll(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke() = repository.deleteAll()
    }
}