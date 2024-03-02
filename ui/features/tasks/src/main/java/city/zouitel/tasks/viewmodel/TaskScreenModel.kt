package city.zouitel.tasks.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.model.Task as InTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskScreenModel(
    private val getAll: TaskUseCase.GetAllTaskItems,
    private val add: TaskUseCase.AddTaskItem,
    private val update: TaskUseCase.UpdateTaskItem,
    private val delete: TaskUseCase.DeleteTaskItem,
    private val mapper: TaskMapper
): ScreenModel {

    private val _getAllTaskList = MutableStateFlow<List<InTask>>(emptyList())
    val getAllTaskList:StateFlow<List<InTask>>
        get() = _getAllTaskList
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        screenModelScope.launch {
            getAll.invoke().collect { list ->
                _getAllTaskList.value = list.map { task -> mapper.toView(task) }
            }
        }
    }

    fun addTotoItem(task: InTask) = screenModelScope.launch(Dispatchers.IO) {
        add.invoke(mapper.toDomain(task))
    }

    fun updateTotoItem(task: InTask) = screenModelScope.launch(Dispatchers.IO) {
        update.invoke(mapper.toDomain(task))
    }

    fun deleteTotoItem(task: InTask) = screenModelScope.launch(Dispatchers.IO) {
        delete.invoke(mapper.toDomain(task))
    }

}