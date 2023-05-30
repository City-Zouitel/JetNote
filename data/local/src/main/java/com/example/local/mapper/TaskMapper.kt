package com.example.local.mapper

import com.example.local.mapper.base.Mapper
import com.example.local.model.TaskEntity
import com.example.repository.model.Task

class TaskMapper: Mapper.Base<TaskEntity, Task> {
    override fun toLocal(data: Task): TaskEntity = with(data){
        TaskEntity(id, item, isDone)
    }

    override fun readOnly(data: TaskEntity): Task = with(data){
        Task(id, item, isDone)
    }
}