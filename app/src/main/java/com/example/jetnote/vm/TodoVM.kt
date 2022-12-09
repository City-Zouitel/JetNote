package com.example.jetnote.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.jetnote.db.entities.todo.Todo
import com.example.jetnote.di.utils.Dispatcher
import com.example.jetnote.di.utils.Dispatchers.*
import com.example.jetnote.reposImp.TodoRepoImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoVM @Inject constructor(
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
    private val repo: TodoRepoImp
): ViewModel() {

    private val _getAllTodoList = MutableStateFlow<List<Todo>>(emptyList())
    val getAllTodoList:StateFlow<List<Todo>>
    get() = _getAllTodoList.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch {
            repo.getAllTodoItems.collect {
                _getAllTodoList.value = it
            }
        }
    }

    fun addTotoItem(item: Todo) =  viewModelScope.launch(ioDispatcher) {
        repo.addTodoItem(item)
    }

    fun updateTotoItem(item: Todo) = viewModelScope.launch(ioDispatcher) {
        repo.updateTodoItem(item)
    }

    fun deleteTotoItem(item: Todo) = viewModelScope.launch(ioDispatcher) {
        repo.deleteTodoItem(item)
    }

}