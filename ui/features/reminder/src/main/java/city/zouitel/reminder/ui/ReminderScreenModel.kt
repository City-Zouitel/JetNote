package city.zouitel.reminder.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.ReminderUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.reminder.mapper.ReminderMapper
import city.zouitel.reminder.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ReminderScreenModel(
    private val observeAll: ReminderUseCase.ObserveAllReminders,
    private val observeById: ReminderUseCase.ObserveReminderById,
    private val insert: ReminderUseCase.InsertReminder,
    private val update: ReminderUseCase.UpdateReminder,
    private val deleteById: ReminderUseCase.DeleteReminderById,
    private val deleteAll: ReminderUseCase.DeleteAllReminders,
    private val mapper: ReminderMapper
): ScreenModel, UiEventHandler<Reminder> {

    private val _observeAllReminders = MutableStateFlow<List<Reminder>>(emptyList())
    val observeAllReminders: StateFlow<List<Reminder>> = _observeAllReminders
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    private val _observeAllById = MutableStateFlow<List<Reminder>>(emptyList())
    fun observeAllById(id: Long): StateFlow<List<Reminder>> = _observeAllById
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    init {
        observeAllReminders()
        observeAllRemindersById()
    }

    private fun observeAllReminders() {
        performUiEvent {
            observeAll().collect { _observeAllReminders.value = mapper.fromDomain(it) }
        }
    }

    private fun observeAllRemindersById(id: Long = 0) {
        performUiEvent {
            observeById(id).collect { _observeAllById.value = mapper.fromDomain(it) }
        }

    }

    override fun sendUiEvent(event: UiEvent<Reminder>) {
        when(event) {
            is UiEvent.Update -> performUiEvent { update(mapper.toDomain(event.data)) }
            is UiEvent.Delete -> performUiEvent { deleteById(event.data.id) }
            is UiEvent.Insert -> performUiEvent { insert(mapper.toDomain(event.data)) }
            is UiEvent.DeleteAll -> performUiEvent { deleteAll() }
            else -> throw NotImplementedError("UiEvent not implemented: $event")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}