package city.zouitel.database.mapper

import city.zouitel.database.model.Task
import city.zouitel.repository.model.Task as OutTask

/**
 * Mapper class responsible for converting between [Task] (domain model) and [OutTask] (repository model).
 *
 * This class provides methods to transform a list of tasks or a single task
 * between the domain and repository representations.
 */
class TaskMapper {

    /**
     * Maps a list of Task objects to a list of their repository representations.
     *
     * @param tasks The list of Tasks objects to map.
     * @return A list containing the repository representations of the given tasks
     * that are mapped from Task to OutTask.
     */
    fun toRepo(tasks: List<Task>) = tasks.map { toRepo(it) }

    /**
     * Maps a Task object to an OutTask object.
     *
     * This function converts a Task object, which is typically used internally,
     * to an OutTask object, which is likely used for external communication or data transfer.
     *
     * @param task The input Task object to be mapped.
     * @return An OutTask object representing the mapped Task.
     */
    fun toRepo(task: Task) = OutTask(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )

    /**
     * Maps an OutTask data object from the repository layer to a Task data object for the repository layer.
     *
     * @param task The OutTask object to map.
     * @return A Task object representing the same data.
     */
    fun fromRepo(task: OutTask) = Task(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )
}