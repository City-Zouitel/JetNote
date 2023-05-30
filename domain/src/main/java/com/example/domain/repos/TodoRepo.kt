package com.example.domain.repos

import com.example.local.model.Task
import kotlinx.coroutines.flow.Flow

interface TodoRepo {
    val getAllTaskItems: Flow<List<Task>>

    suspend fun addTodoItem(item: Task)

    suspend fun updateTodoItem(item: Task)

    suspend fun deleteTodoItem(item: Task)
}