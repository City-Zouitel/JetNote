package city.zouitel.repository.mapper

import city.zouitel.repository.mapper.base.Mapper
import city.zouitel.repository.model.Task as InTask
import city.zouitel.domain.model.Task as OutTask

class TaskMapper: Mapper.Base<InTask, OutTask> {
    override fun toRepository(data: OutTask): InTask = with(data){
        InTask(id, item, isDone)
    }

    override fun toDomain(data: InTask): OutTask = with(data){
        OutTask(id, item, isDone)
    }
}