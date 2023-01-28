package com.example.local.daos

import androidx.room.*
import com.example.local.model.Todo
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("select * from todo")
    fun getAllTodoItems():Flow<List<Todo>>

    @Insert
    suspend fun addTodoItem(item: Todo)

    @Update
    suspend fun updateTodoItem(item: Todo)

    @Delete
    suspend fun deleteTodoItem(item: Todo)

}