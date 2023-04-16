package com.example.domain.reposImpl

import com.example.domain.repos.TodoRepo
import com.example.local.daos.TodoDao
import com.example.local.model.Todo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class TodoRepoImpl @Inject constructor(
//    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val dao: TodoDao
): TodoRepo {
    override val getAllTodoItems: Flow<List<Todo>>
        get() = dao.getAllTodoItems()

    override suspend fun addTodoItem(item: Todo) = withContext(Dispatchers.IO) { dao.addTodoItem(item) }

    override suspend fun updateTodoItem(item: Todo) = withContext(Dispatchers.IO) { dao.updateTodoItem(item) }

    override suspend fun deleteTodoItem(item: Todo) = withContext(Dispatchers.IO) { dao.deleteTodoItem(item) }
}