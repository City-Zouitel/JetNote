package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.reposImpl.TodoRepoImp
import com.example.domain.utils.Dispatcher
import com.example.local.model.Todo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoVM @Inject constructor(
//    private val ioDispatcher: CoroutineDispatcher,
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

    fun addTotoItem(item: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repo.addTodoItem(item)
    }

    fun updateTotoItem(item: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repo.updateTodoItem(item)
    }

    fun deleteTotoItem(item: Todo) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteTodoItem(item)
    }

}