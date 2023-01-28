package com.example.domain.repos

import com.example.local.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepo {
    val getAllTodoItems: Flow<List<Todo>>

    suspend fun addTodoItem(item: Todo)

    suspend fun updateTodoItem(item: Todo)

    suspend fun deleteTodoItem(item: Todo)
}