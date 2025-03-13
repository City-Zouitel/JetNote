package city.zouitel.domain.usecase

import city.zouitel.domain.model.Task
import city.zouitel.domain.repository.TaskRepository

/**
 * Sealed class representing various use cases for interacting with tasks.
 * Each use case encapsulates a specific operation on the [TaskRepository].
 */
sealed class TaskUseCase {

    /**
     * ObserveAll is a use case that provides a stream of all tasks from the repository.
     *
     * This use case delegates the responsibility of fetching and observing all tasks
     * to the underlying [TaskRepository]. It exposes the data as a Flow of [Task]
     * objects, allowing for reactive updates when the underlying data changes.
     *
     * @property repo The [TaskRepository] used to interact with the task data source.
     */
    data class ObserveAll(private val repo: TaskRepository): TaskUseCase() {
        operator fun invoke() = repo.observeAll
    }

    /**
     *  UseCase to observe a [Task] by its unique identifier (UID).
     *
     *  This UseCase utilizes the [TaskRepository] to provide a Flow of [Task] objects
     *  that match the specified UID.  It allows for observing real-time updates to a
     *  specific task.
     *
     *  @property repo The [TaskRepository] instance used to access task data.
     *  @constructor Creates an ObserveByUid instance with the given [TaskRepository].
     */
    data class ObserveByUid(private val repo: TaskRepository): TaskUseCase() {
        operator fun invoke(uid: String) = repo.observeByUid(uid)
    }

    /**
     * [Insert] is a use case responsible for inserting a new [Task] into the data layer.
     *
     * It utilizes a [TaskRepository] to handle the underlying data persistence.
     *
     * @property repo The [TaskRepository] instance used for data operations.
     */
    data class Insert(private val repo: TaskRepository): TaskUseCase() {
        suspend operator fun invoke(task: Task) = repo.insert(task)
    }

    /**
     * [TaskUseCase] for deleting a task by its ID.
     *
     * This use case encapsulates the logic for deleting a task from the underlying
     * [TaskRepository] based on the provided ID.
     *
     * @property repo The [TaskRepository] responsible for interacting with the data source.
     */
    data class Delete(private val repo: TaskRepository): TaskUseCase() {
        suspend operator fun invoke(id: Long) = repo.delete(id)
    }

    /**
     * `DeleteDrafts` is a use case class responsible for deleting all draft tasks from the repository.
     *
     * This class encapsulates the logic for deleting draft tasks, providing a clean and testable way
     * to perform this operation. It leverages the `TaskRepository` to interact with the data layer.
     *
     * @property repo The `TaskRepository` instance used to access and modify task data.
     * @constructor Creates a `DeleteDrafts` instance with the specified `TaskRepository`.
     */
    data class DeleteDrafts(private val repo: TaskRepository): TaskUseCase() {
        suspend operator fun invoke() = repo.deleteDrafts()
    }
}
