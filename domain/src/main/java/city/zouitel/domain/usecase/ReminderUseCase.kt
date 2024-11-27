package city.zouitel.domain.usecase

import city.zouitel.domain.model.Reminder
import city.zouitel.domain.repository.ReminderDatasource

sealed class ReminderUseCase {

    data class ObserveById(val repository: ReminderDatasource): ReminderUseCase() {
        suspend operator fun invoke(uid: String) = repository.observeById(uid)
    }

    data class Insert(val repository: ReminderDatasource): ReminderUseCase() {
        suspend operator fun invoke(reminder: Reminder) = repository.insert(reminder)
    }

    data class Update(val repository: ReminderDatasource): ReminderUseCase() {
        suspend operator fun invoke(reminder: Reminder) = repository.update(reminder)
    }

    data class DeleteReminder(val repository: ReminderDatasource): ReminderUseCase() {
        suspend operator fun invoke(id: Long) = repository.delete(id)
    }

    data class DeleteAll(val repository: ReminderDatasource): ReminderUseCase() {
        suspend operator fun invoke() = repository.deleteAll()
    }
}