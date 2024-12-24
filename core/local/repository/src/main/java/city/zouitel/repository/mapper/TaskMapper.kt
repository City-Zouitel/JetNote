package city.zouitel.repository.mapper

import city.zouitel.repository.model.Task
import city.zouitel.domain.model.Task as OutTask

class TaskMapper {
    fun toDomain(tasks: List<Task>) = tasks.map { toDomain(it) }

    fun toDomain(task: Task) = OutTask(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )

    fun fromDomain(task: OutTask) = Task(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )
}