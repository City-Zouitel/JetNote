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
    private val observeById: ReminderUseCase.ObserveById,
    private val insert: ReminderUseCase.Insert,
    private val update: ReminderUseCase.Update,
    private val delete: ReminderUseCase.DeleteReminder,
    private val deleteAll: ReminderUseCase.DeleteAll,
    private val mapper: ReminderMapper
): ScreenModel, UiEventHandler<Reminder> {

    private val _observeAllById = MutableStateFlow<List<Reminder>>(emptyList())
    val observeAllById: StateFlow<List<Reminder>> = _observeAllById
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    fun observeRemindersByUid(uid: String) {
        performUiEvent {
            observeById(uid).collect { _observeAllById.value = mapper.fromDomain(it) }
        }
    }

    override fun sendUiEvent(event: UiEvent<Reminder>) {
        when(event) {
            is UiEvent.Update -> performUiEvent { update(mapper.toDomain(event.data)) }
            is UiEvent.Delete -> performUiEvent { delete(event.data.id) }
            is UiEvent.Insert -> performUiEvent { insert(mapper.toDomain(event.data)) }
            is UiEvent.DeleteAll -> performUiEvent { deleteAll() }
            else -> throw NotImplementedError("UiEvent not implemented: $event")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}