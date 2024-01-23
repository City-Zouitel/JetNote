package city.zouitel.tasks.mapper

import city.zouitel.domain.model.Task as OutTask
import city.zouitel.tasks.mapper.base.Mapper
import city.zouitel.tasks.model.Task as InTask

class TaskMapper: Mapper.Base<InTask, OutTask> {
    override fun toView(data: OutTask): InTask = with((data)) {
        InTask(id, item, isDone)
    }

    override fun toDomain(data: InTask): OutTask = with(data) {
        OutTask(id, item, isDone)
    }
}