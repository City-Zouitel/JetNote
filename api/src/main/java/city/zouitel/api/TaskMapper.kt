package city.zouitel.api

import city.zouitel.api.Task as InTask
import com.example.domain.model.Task as OutTask

class TaskMapper: Mapper.Base<InTask, OutTask> {
    override fun toView(data: OutTask): InTask = with((data)) {
        InTask(id, item, isDone)
    }

    override fun toDomain(data: InTask): OutTask = with(data) {
        OutTask(id, item, isDone)
    }
}