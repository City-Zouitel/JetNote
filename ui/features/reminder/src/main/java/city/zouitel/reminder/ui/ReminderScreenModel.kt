package city.zouitel.reminder.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.ReminderUseCase
import city.zouitel.logic.withFlow
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.reminder.mapper.ReminderMapper
import city.zouitel.reminder.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReminderScreenModel(
    private val observeById: ReminderUseCase.ObserveById,
    private val insert: ReminderUseCase.Insert,
    private val delete: ReminderUseCase.DeleteReminder,
    private val deleteAll: ReminderUseCase.DeleteAll,
    private val mapper: ReminderMapper
): ScreenModel, UiEventHandler<Reminder> {

    private val _observeAllById = MutableStateFlow<List<Reminder>>(emptyList())
    val observeAllById: StateFlow<List<Reminder>> = _observeAllById.withFlow(emptyList())

    fun initializeReminders(uid: String) {
        performUiEvent {
            observeById(uid).collect {
                _observeAllById.value = mapper.fromDomain(it)
            }
        }
    }

    override fun sendUiEvent(event: UiEvent<Reminder>) {
        when(event) {
            is UiEvent.Delete -> performUiEvent { delete(event.data.id) }
            is UiEvent.Insert -> performUiEvent { insert(mapper.toDomain(event.data)) }
            is UiEvent.DeleteAll -> performUiEvent { deleteAll() }
            else -> throw NotImplementedError("Action not implemented: $event")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}