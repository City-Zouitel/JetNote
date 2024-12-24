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
     * @property repo The Reminder repo used to fetch and observe the Reminder.
     */
    data class ObserveById(val repo: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(uid: String) = repo.observeById(uid)
    }

    /**
     * Use case for inserting a new reminder into the repo.
     *
     * @param repo The [ReminderRepo] to use for inserting the reminder.
     */
    data class Insert(val repo: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(reminder: Reminder) = repo.insert(reminder)
    }

    /**
     * A use case responsible for updating a reminder.
     *
     * This use case interacts with the [ReminderRepo] to update a reminder associated with the given ID.
     *
     * @param repo The repo used to access and modify reminder data.
     */
    data class Update(val repo: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(id: Int) = repo.update(id)
    }

    /**
     * Use case for deleting a reminder.
     *
     * This class provides a way to delete a reminder from the repo using its ID.
     *
     * @property repo The repo used to access reminder data.
     */
    data class DeleteReminder(val repo: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(id: Int) = repo.delete(id)
    }

    /**
     * Use case for deleting all reminders.
     *
     * This use case utilizes the provided `ReminderRepo` to delete all reminders.
     *
     * @property repo The repo responsible for storing and retrieving reminders.
     */
    data class DeleteAll(val repo: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke() = repo.deleteAll()
    }
}