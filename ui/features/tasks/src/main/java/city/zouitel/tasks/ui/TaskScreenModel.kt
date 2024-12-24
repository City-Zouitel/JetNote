package city.zouitel.tasks.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.logic.asLogicFlow
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TaskScreenModel(
    private val observeAll: TaskUseCase.ObserveAll,
    private val observeByUid: TaskUseCase.ObserveByUid,
    private val insert: TaskUseCase.Insert,
    private val updateById: TaskUseCase.UpdateById,
    private val deleteById: TaskUseCase.DeleteById,
    private val mapper: TaskMapper
): ScreenModel, UiEventHandler<Task> {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asLogicFlow(UiState())

    private val _getAllTaskList = MutableStateFlow<List<Task>>(emptyList())
    val getAllTaskList:StateFlow<List<Task>> = _getAllTaskList.asLogicFlow(listOf())

    private val _observeAllTasks = MutableStateFlow<List<Task>>(emptyList())
    val observeAllTasks:StateFlow<List<Task>> = _observeAllTasks.asLogicFlow(listOf())

    fun initializeTasks(uid: String) {
        performUiEvent {
            observeByUid(uid).collect {
                _getAllTaskList.value =  mapper.fromDomain(it)
            }
        }
    }

    init {
        performUiEvent {
            observeAll().collect {
                _observeAllTasks.value = mapper.fromDomain(it)
            }
        }
    }

    override fun sendUiEvent(event: UiEvent<Task>) {
        when(event) {
            is UiEvent.Update -> performUiEvent { updateById(event.data.id) }
            is UiEvent.Delete -> performUiEvent { deleteById(event.data.id) }
            is UiEvent.Insert -> performUiEvent { insert(mapper.toDomain(event.data)) }
            else -> throw Exception("Not implemented")
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