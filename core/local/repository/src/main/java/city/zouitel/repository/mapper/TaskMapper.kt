package city.zouitel.repository.mapper

import city.zouitel.repository.model.Task
import city.zouitel.domain.model.Task as OutTask

/**
 * Mapper class responsible for converting between [Task] (repository layer representation) and
 * [OutTask] (domain layer representation) objects.
 */
class TaskMapper {

    /**
     * Converts a list of [Task] entities to a list of corresponding domain [Task] models.
     *
     * This function iterates through a list of [Task] entities and applies the [toDomain(Task)]
     * conversion function to each entity, resulting in a new list containing the domain
     * representations of those tasks.
     *
     * @param tasks The list of [Task] entities to convert.
     * @return A list of [Task] domain models, where each model corresponds to a
     *         converted entity from the input list.
     *
     * @see toDomain(Task)
     */
    fun toDomain(tasks: List<Task>) = tasks.map { toDomain(it) }

    /**
     * Converts a [Task] entity repository model to an [OutTask] domain model.
     *
     * This function maps the properties of a [Task] object to a corresponding [OutTask] object,
     * effectively transforming a data-layer representation of a task into a domain-layer
     * representation. The mapping includes the task's ID, UID, item description, and completion status.
     *
     * @param task The [Task] entity to convert.
     * @return An [OutTask] domain model representing the provided task.
     */
    fun toDomain(task: Task) = OutTask(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )

    /**
     * Converts an [OutTask] (domain model) to a [Task] (repository model).
     *
     * This function maps the properties of an [OutTask] to a new [Task] instance.
     * It's typically used when transferring data from the domain layer to the
     * data/presentation layer.
     *
     * @param task The [OutTask] object to convert.
     * @return A new [Task] object with properties copied from the input [OutTask].
     */
    fun fromDomain(task: OutTask) = Task(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )
}