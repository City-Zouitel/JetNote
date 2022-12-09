package com.example.jetnote.reposImp

import com.example.jetnote.db.daos.TodoDao
import com.example.jetnote.db.entities.todo.Todo
import com.example.jetnote.di.utils.Dispatcher
import com.example.jetnote.di.utils.Dispatchers
import com.example.jetnote.repos.TodoRepo
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class TodoRepoImp @Inject constructor(
    @Dispatcher(Dispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val dao:TodoDao
): TodoRepo {
    override val getAllTodoItems: Flow<List<Todo>>
        get() = dao.getAllTodoItems()

    override suspend fun addTodoItem(item: Todo) = withContext(ioDispatcher) { dao.addTodoItem(item) }

    override suspend fun updateTodoItem(item: Todo) = withContext(ioDispatcher) { dao.updateTodoItem(item) }

    override suspend fun deleteTodoItem(item: Todo) = withContext(ioDispatcher) { dao.deleteTodoItem(item) }
}