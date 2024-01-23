package city.zouitel.database.mapper

import city.zouitel.database.mapper.base.Mapper
import city.zouitel.database.model.TaskEntity as InTask
import city.zouitel.repository.model.Task as OutTask

class TaskMapper: Mapper.Base<InTask, OutTask> {
    override fun toLocal(data: OutTask): InTask = with(data){
        InTask(id, item, isDone)
    }

    override fun readOnly(data: InTask): OutTask = with(data){
        OutTask(id, item, isDone)
    }
}