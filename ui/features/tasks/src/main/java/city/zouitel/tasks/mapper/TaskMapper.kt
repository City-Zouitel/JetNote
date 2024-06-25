package city.zouitel.tasks.mapper

import city.zouitel.domain.model.Task as OutTask
import city.zouitel.tasks.mapper.base.Mapper
import city.zouitel.tasks.model.Task as InTask

class TaskMapper {
    fun fromDomain(tasks: List<OutTask>) = tasks.map { fromDomain(it) }

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