package com.example.domain.repository

import com.example.domain.model.Task as OutTask
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    val getAllTaskItems: Flow<List<OutTask>>

    suspend fun addTaskItem(task: OutTask)

    suspend fun updateTaskItem(task: OutTask)

    suspend fun deleteTaskItem(task: OutTask)
}