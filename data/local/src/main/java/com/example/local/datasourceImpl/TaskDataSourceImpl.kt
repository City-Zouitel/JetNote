package com.example.local.datasourceImpl

import com.example.local.dao.TaskDao
import com.example.local.mapper.TaskMapper
import com.example.repository.datasource.TaskDataSource
import com.example.repository.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskDataSourceImpl @Inject constructor(
    private val taskDao: TaskDao,
    private val taskMapper: TaskMapper
): TaskDataSource {
    override val getAllTaskItems: Flow<List<Task>>
        get() = taskDao.getAllTaskItems().map { list ->
            list.map {
                taskMapper.readOnly(it)
            }
        }

    override suspend fun addTaskItem(task: Task) {
        taskDao.addTaskItem(taskMapper.toLocal(task))
    }

    override suspend fun updateTaskItem(task: Task) {
        taskDao.updateTaskItem(taskMapper.toLocal(task))
    }

    override suspend fun deleteTaskItem(task: Task) {
        taskDao.deleteTaskItem(taskMapper.toLocal(task))
    }
}