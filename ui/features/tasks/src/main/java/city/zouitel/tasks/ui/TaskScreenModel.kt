package city.zouitel.tasks.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.state.UiState
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

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState()
    )
    val uiState: StateFlow<UiState> = _uiState
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            UiState()
        )


    private val _getAllTaskList = MutableStateFlow<List<InTask>>(emptyList())
    val getAllTaskList:StateFlow<List<InTask>> = _getAllTaskList.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        screenModelScope.launch {
            getAll.invoke().collect { tasks -> _getAllTaskList.value =  mapper.fromDomain(tasks) }
        }
    }

    fun addTotoItem(task: InTask) {
        screenModelScope.launch(Dispatchers.IO) {
            add.invoke(mapper.toDomain(task))
        }
    }

    fun updateTotoItem(task: InTask)  {
        screenModelScope.launch(Dispatchers.IO) {
            update.invoke(mapper.toDomain(task))
        }
    }

    fun deleteTotoItem(task: InTask) {
        screenModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(task))
        }
    }

    fun updateId(id: Long = 0L): TaskScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(currentId = id)
        }
        return this
    }


    fun updateItem(item: String = ""): TaskScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(currentItem = item)
        }
        return this
    }
}