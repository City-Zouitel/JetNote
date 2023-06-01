package com.example.tasks.mapper

import com.example.domain.model.Task as OutTask
import com.example.tasks.mapper.base.Mapper
import com.example.tasks.model.Task as InTask

class TaskMapper: Mapper.Base<InTask, OutTask> {
    override fun toView(data: OutTask): InTask = with((data)) {
        InTask(id, item, isDone)
    }

    override fun toDomain(data: InTask): OutTask = with(data) {
        OutTask(id, item, isDone)
    }
}