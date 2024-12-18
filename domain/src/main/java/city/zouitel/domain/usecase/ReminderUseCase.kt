package city.zouitel.domain.usecase

import city.zouitel.domain.model.Reminder
import city.zouitel.domain.repository.ReminderRepo

/**
 * Represents different use cases for interacting with reminders.
 * This sealed class ensures that all possible use cases are defined and handled.
 */
sealed class ReminderUseCase {

    /**
     * A UseCase for observing a Reminder by its unique ID.
     *
     * This UseCase utilizes the provided [ReminderRepo] to access and observe the Reminder.
     *
     * @property repository The Reminder repository used to fetch and observe the Reminder.
     */
    data class ObserveById(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(uid: String) = repository.observeById(uid)
    }

    /**
     * Use case for inserting a new reminder into the repository.
     *
     * @param repository The [ReminderRepo] to use for inserting the reminder.
     */
    data class Insert(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(reminder: Reminder) = repository.insert(reminder)
    }

    /**
     * A use case responsible for updating a reminder.
     *
     * This use case interacts with the [ReminderRepo] to update a reminder associated with the given ID.
     *
     * @param repository The repository used to access and modify reminder data.
     */
    data class Update(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(id: Int) = repository.update(id)
    }

    /**
     * Use case for deleting a reminder.
     *
     * This class provides a way to delete a reminder from the repository using its ID.
     *
     * @property repository The repository used to access reminder data.
     */
    data class DeleteReminder(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(id: Int) = repository.delete(id)
    }

    /**
     * Use case for deleting all reminders.
     *
     * This use case utilizes the provided `ReminderRepo` to delete all reminders.
     *
     * @property repository The repository responsible for storing and retrieving reminders.
     */
    data class DeleteAll(val repository: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke() = repository.deleteAll()
    }
}