package city.zouitel.reminder.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndReminderUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.reminder.mapper.NoteAndReminderMapper
import city.zouitel.reminder.model.NoteAndReminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteAndReminderScreenModel(
    private val observeAll: NoteAndReminderUseCase.ObserveAllNotesAndReminders,
    private val observeById: NoteAndReminderUseCase.ObserveNoteAndRemindersById,
    private val add: NoteAndReminderUseCase.InsertNoteAndReminder,
    private val update: NoteAndReminderUseCase.UpdateNoteAndReminder,
    private val delete: NoteAndReminderUseCase.DeleteNoteAndReminderById,
    private val deleteAll: NoteAndReminderUseCase.DeleteAllNotesAndReminders,
    private val mapper: NoteAndReminderMapper
): ScreenModel, UiEventHandler<NoteAndReminder> {

    private val _getAllNotesAndReminders = MutableStateFlow<List<NoteAndReminder>>(emptyList())
    val getAllNotesAndReminders: StateFlow<List<NoteAndReminder>> = _getAllNotesAndReminders
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    private val _getByIdNotesAndReminders = MutableStateFlow<List<NoteAndReminder>>(emptyList())
    fun getByIdNotesAndReminders(noteId: String): StateFlow<List<NoteAndReminder>> = _getByIdNotesAndReminders
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    init {
        performUiEvent {
            observeAll().collect { _getAllNotesAndReminders.value = mapper.fromDomain(it) }
        }
        performUiEvent {
            observeById("").collect { _getByIdNotesAndReminders.value = mapper.fromDomain(it) }
        }
    }

    override fun sendUiEvent(event: UiEvent<NoteAndReminder>) {
        when(event) {
            is UiEvent.Update -> performUiEvent { update(mapper.toDomain(event.data)) }
            is UiEvent.Delete -> performUiEvent { delete(event.data.noteId) }
            is UiEvent.Insert -> performUiEvent { add(mapper.toDomain(event.data)) }
            is UiEvent.DeleteAll -> performUiEvent { deleteAll() }
            else -> throw Exception("Not implemented!")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}