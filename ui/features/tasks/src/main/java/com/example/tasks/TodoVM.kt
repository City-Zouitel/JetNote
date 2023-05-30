package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.reposImpl.TodoRepoImpl
import com.example.local.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val repo: TodoRepoImpl
): ViewModel() {

    private val _getAllTaskList = MutableStateFlow<List<Task>>(emptyList())
    val getAllTaskList:StateFlow<List<Task>>
    get() = _getAllTaskList.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch {
            repo.getAllTaskItems.collect {
                _getAllTaskList.value = it
            }
        }
    }

    fun addTotoItem(item: Task) = viewModelScope.launch(Dispatchers.IO) {
        repo.addTodoItem(item)
    }

    fun updateTotoItem(item: Task) = viewModelScope.launch(Dispatchers.IO) {
        repo.updateTodoItem(item)
    }

    fun deleteTotoItem(item: Task) = viewModelScope.launch(Dispatchers.IO) {
        repo.deleteTodoItem(item)
    }

}