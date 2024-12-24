package city.zouitel.database.mapper

import city.zouitel.database.model.Task
import city.zouitel.repository.model.Task as OutTask

class TaskMapper {

    fun toRepo(tasks: List<Task>) = tasks.map { toRepo(it) }

    fun toRepo(task: Task) = OutTask(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )

    fun fromRepo(task: OutTask) = Task(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )
}