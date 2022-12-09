package com.example.jetnote.repos

import com.example.jetnote.db.entities.todo.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepo {
    val getAllTodoItems: Flow<List<Todo>>

    suspend fun addTodoItem(item: Todo)

    suspend fun updateTodoItem(item: Todo)

    suspend fun deleteTodoItem(item: Todo)
}