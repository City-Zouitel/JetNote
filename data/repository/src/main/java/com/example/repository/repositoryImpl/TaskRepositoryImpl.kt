package com.example.repository.repositoryImpl

import com.example.domain.model.Task as OutTask
import com.example.domain.repository.TaskRepository
import com.example.repository.datasource.TaskDataSource
import com.example.repository.mapper.TaskMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val dataSource: TaskDataSource,
    private val mapper: TaskMapper
): TaskRepository {
    override val getAllTaskItems: Flow<List<OutTask>>
        get() = dataSource.getAllTaskItems.map { list ->
            list.map { task ->
                mapper.readOnly(task)
            }
        }

    override suspend fun addTaskItem(task: OutTask) {
        dataSource.addTaskItem(mapper.toRepository(task))
    }

    override suspend fun updateTaskItem(task: OutTask) {
        dataSource.updateTaskItem(mapper.toRepository(task))
    }

    override suspend fun deleteTaskItem(task: OutTask) {
        dataSource.deleteTaskItem(mapper.toRepository(task))
    }
}