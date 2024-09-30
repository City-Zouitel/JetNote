package city.zouitel.repository.mapper

import city.zouitel.repository.model.Task as InTask
import city.zouitel.domain.model.Task as OutTask

class TaskMapper {
    fun toDomain(tasks: List<InTask>) = tasks.map { toDomain(it) }

    fun toDomain(task: InTask) = OutTask(
        id = task.id,
        item = task.item,
        isDone = task.isDone
    )

    fun fromDomain(task: OutTask) = InTask(
        id = task.id,
        item = task.item,
        isDone = task.isDone
    )
}