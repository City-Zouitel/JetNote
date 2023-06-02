package com.example.repository.mapper

import com.example.repository.mapper.base.Mapper
import com.example.repository.model.Task as InTask
import com.example.domain.model.Task as OutTask

class TaskMapper: Mapper.Base<InTask, OutTask> {
    override fun toRepository(data: OutTask): InTask = with(data){
        InTask(id, item, isDone)
    }

    override fun toDomain(data: InTask): OutTask = with(data){
        OutTask(id, item, isDone)
    }
}