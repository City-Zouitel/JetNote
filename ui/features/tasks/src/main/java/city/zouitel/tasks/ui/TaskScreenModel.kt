package city.zouitel.tasks.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TaskUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.logic.withFlow
import city.zouitel.tasks.mapper.TaskMapper
import city.zouitel.tasks.model.Task
import city.zouitel.tasks.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Suppress("DeferredResultUnused")
class TaskScreenModel(
    private val observeAll: TaskUseCase.ObserveAll,
    private val observeByUid: TaskUseCase.ObserveByUid,
    private val insert: TaskUseCase.Insert,
    private val deleteById: TaskUseCase.DeleteById,
    private val deleteByUid: TaskUseCase.DeleteByUid,
    private val mapper: TaskMapper
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.withFlow(UiState())

    private val _getAllTaskList = MutableStateFlow<List<Task>>(emptyList())
    val getAllTaskList: StateFlow<List<Task>> = _getAllTaskList.withFlow(listOf())

    private val _observeAllTasks = MutableStateFlow<List<Task>>(emptyList())
    val observeAllTasks: StateFlow<List<Task>> = _observeAllTasks
        .withFlow(listOf()) {
            withAsync {
                observeAll().collect { _observeAllTasks.value = mapper.fromDomain(it) }
            }
        }

    fun initializeTasks(uid: String) {
        withAsync {
            observeByUid(uid).collect {
                _getAllTaskList.value =  mapper.fromDomain(it)
            }
        }
    }

    fun sendAction(act: Action) {
        when(act) {
            is Action.Insert<*> -> withAsync { insert(mapper.toDomain(act.data as Task)) }
            is Action.DeleteById -> withAsync { deleteById(act.id as Long) }
            is Action.DeleteByUid -> withAsync { deleteByUid(act.uid) }
            else -> throw Exception("Action is not implemented $act")
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