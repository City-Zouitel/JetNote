package com.example.domain.reposImpl

import com.example.jetnote.di.utils.Dispatcher
import com.example.jetnote.di.utils.Dispatchers
import com.example.domain.repos.TodoRepo
import com.example.local.daos.TodoDao
import com.example.local.model.Todo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class TodoRepoImp @Inject constructor(
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val dao: TodoDao
): TodoRepo {
    override val getAllTodoItems: Flow<List<Todo>>
        get() = dao.getAllTodoItems()

    override suspend fun addTodoItem(item: Todo) = withContext(ioDispatcher) { dao.addTodoItem(item) }

    override suspend fun updateTodoItem(item: Todo) = withContext(ioDispatcher) { dao.updateTodoItem(item) }

    override suspend fun deleteTodoItem(item: Todo) = withContext(ioDispatcher) { dao.deleteTodoItem(item) }
}