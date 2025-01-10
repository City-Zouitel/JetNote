package city.zouitel.tasks.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.logic.withFlow
import city.zouitel.logic.events.UiEvents
import city.zouitel.logic.events.UiEventsHandler
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class TaskScreenModel(
    private val observeAll: TaskUseCase.ObserveAll,
    private val observeByUid: TaskUseCase.ObserveByUid,
    private val insert: TaskUseCase.Insert,
    private val update: TaskUseCase.Update,
    private val updateById: TaskUseCase.UpdateById,
    private val deleteById: TaskUseCase.DeleteById,
    private val mapper: TaskMapper
): ScreenModel, UiEventsHandler {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.withFlow(UiState())

    private val _getAllTaskList = MutableStateFlow<List<Task>>(emptyList())
    val getAllTaskList: StateFlow<List<Task>> = _getAllTaskList.withFlow(listOf())

    private val _observeAllTasks = MutableStateFlow<List<Task>>(emptyList())
    val observeAllTasks: StateFlow<List<Task>> = _observeAllTasks
        .onStart {
            performUiEvent {
                observeAll().collect {
                    _observeAllTasks.value = mapper.fromDomain(it)
                }
            }
        }
        .withFlow(listOf())

    fun initializeTasks(uid: String) {
        performUiEvent {
            observeByUid(uid).collect {
                _getAllTaskList.value =  mapper.fromDomain(it)
            }
        }
    }

    override fun sendUiEvent(event: UiEvents) {
        when(event) {
            is UiEvents.Insert<*> -> performUiEvent { insert(mapper.toDomain(event.data as Task)) }
            is UiEvents.Update<*> -> performUiEvent {
                when(event.data) {
                    is Task -> update(mapper.toDomain(event.data as Task))
                    is Long -> updateById(event.data as Long)
                }
            }
            is UiEvents.Delete<*> -> performUiEvent { deleteById(event.data as Long) }
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