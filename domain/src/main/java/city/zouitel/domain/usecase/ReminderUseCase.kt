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
    data class ObserveByUid(val repo: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(uid: String) = repo.observeByUid(uid)
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
    data class Delete(val repo: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke(id: Int) = repo.delete(id)
    }

    /**
     * Data class representing the use case for deleting draft reminders.
     *
     * This use case encapsulates the logic for deleting all reminders that are marked as drafts.
     * It interacts with the [ReminderRepo] to perform the actual deletion operation.
     *
     * @property repo The [ReminderRepo] instance used to interact with the reminder data source.
     */
    data class DeleteDrafts(val repo: ReminderRepo): ReminderUseCase() {
        suspend operator fun invoke() = repo.deleteDrafts()
    }
}