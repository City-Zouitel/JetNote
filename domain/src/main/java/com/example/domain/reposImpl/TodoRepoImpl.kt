package com.example.domain.reposImpl

import com.example.domain.repos.TodoRepo
import com.example.local.dao.TaskDao
import com.example.local.model.Task
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class TodoRepoImpl @Inject constructor(
//    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val dao: TaskDao
): TodoRepo {
    override val getAllTaskItems: Flow<List<Task>>
        get() = dao.getAllTodoItems()

    override suspend fun addTodoItem(item: Task) = withContext(Dispatchers.IO) { dao.addTodoItem(item) }

    override suspend fun updateTodoItem(item: Task) = withContext(Dispatchers.IO) { dao.updateTodoItem(item) }

    override suspend fun deleteTodoItem(item: Task) = withContext(Dispatchers.IO) { dao.deleteTodoItem(item) }
}