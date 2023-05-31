package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.TaskEntity as InTask
import com.example.repository.model.Task as OutTask

class TaskMapper: Mapper.Base<InTask, OutTask> {
    override fun toLocal(data: OutTask): InTask = with(data){
        InTask(id, item, isDone)
    }

    override fun readOnly(data: InTask): OutTask = with(data){
        OutTask(id, item, isDone)
    }
}