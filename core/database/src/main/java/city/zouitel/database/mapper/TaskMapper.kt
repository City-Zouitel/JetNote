package city.zouitel.database.mapper

import city.zouitel.repository.model.Task
import city.zouitel.database.model.TaskEntity as InTask
import city.zouitel.repository.model.Task as OutTask

class TaskMapper {

    fun toRepo(tasks: List<InTask>) = tasks.map { toRepo(it) }

    fun toRepo(task: InTask) = OutTask(
        id = task.id,
        item = task.item,
        isDone = task.isDone
    )

    fun fromRepo(task: OutTask) = InTask(
        id = task.id,
        item = task.item,
        isDone = task.isDone
    )
}