package city.zouitel.tasks.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import city.zouitel.tasks.model.Task as InTask

class TaskScreenModel(
    private val getAll: TaskUseCase.GetAllTaskItems,
    private val add: TaskUseCase.AddTaskItem,
    private val update: TaskUseCase.UpdateTaskItem,
    private val delete: TaskUseCase.DeleteTaskItem,
    private val mapper: TaskMapper
): ScreenModel, UiEventHandler<InTask> {

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
        performUiEvent {
            getAll().collect { tasks -> _getAllTaskList.value =  mapper.fromDomain(tasks) }
        }
    }

    override fun sendUiEvent(event: UiEvent<InTask>) {
        when(event) {
            is UiEvent.Update -> performUiEvent { update(mapper.toDomain(event.data)) }
            is UiEvent.Delete -> performUiEvent { delete(mapper.toDomain(event.data)) }
            is UiEvent.Insert -> performUiEvent { add(mapper.toDomain(event.data)) }
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
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