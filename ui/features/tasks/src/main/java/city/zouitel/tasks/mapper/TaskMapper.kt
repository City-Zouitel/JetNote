package city.zouitel.tasks.mapper

import city.zouitel.tasks.model.Task
import city.zouitel.domain.model.Task as OutTask

class TaskMapper {
    fun fromDomain(tasks: List<OutTask>) = tasks.map { fromDomain(it) }

    fun toDomain(task: Task) = OutTask(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )

    private fun fromDomain(task: OutTask) = Task(
        id = task.id,
        uid = task.uid,
        item = task.item,
        isDone = task.isDone
    )
}