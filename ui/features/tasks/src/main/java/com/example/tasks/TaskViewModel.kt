package com.example.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.TaskUseCase
import com.example.tasks.mapper.TaskMapper
import com.example.tasks.model.Task as InTask
import com.example.domain.model.Task as OutTask
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val getAll: TaskUseCase.GetAllTaskItems,
    private val add: TaskUseCase.AddTaskItem,
    private val update: TaskUseCase.UpdateTaskItem,
    private val delete: TaskUseCase.DeleteTaskItem,
    private val mapper: TaskMapper
): ViewModel() {

    private val _getAllTaskList = MutableStateFlow<List<OutTask>>(emptyList())
    val getAllTaskList:StateFlow<List<OutTask>>
    get() = _getAllTaskList.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        viewModelScope.launch {
            getAll.invoke().collect {
                _getAllTaskList.value = it
            }
        }
    }

    fun addTotoItem(task: InTask) = viewModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(task))
    }

    fun updateTotoItem(task: InTask) = viewModelScope.launch(Dispatchers.IO) {
        update.invoke(mapper.toDomain(task))
    }

    fun deleteTotoItem(task: InTask) = viewModelScope.launch(Dispatchers.IO) {
        delete.invoke(mapper.toDomain(task))
    }

}