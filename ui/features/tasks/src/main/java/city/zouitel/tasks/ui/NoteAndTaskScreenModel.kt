package city.zouitel.tasks.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndTaskUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.tasks.mapper.NoteAndTaskMapper
import city.zouitel.tasks.model.NoteAndTask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import city.zouitel.tasks.model.NoteAndTask as InNoteAndTask

class NoteAndTaskScreenModel(
    getAll: NoteAndTaskUseCase.GetAllNotesAndTask,
    private val add: NoteAndTaskUseCase.AddNoteAndTask,
    private val delete: NoteAndTaskUseCase.DeleteNoteAndTask,
    private val mapper: NoteAndTaskMapper
): ScreenModel, UiEventHandler<NoteAndTask> {

    private val _getAllNotesAndTask: MutableStateFlow<List<NoteAndTask>> = MutableStateFlow(
        emptyList()
    )
    val getAllNotesAndTask: StateFlow<List<InNoteAndTask>>
        get() = _getAllNotesAndTask
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        performUiEvent {
            getAll().collect { notesAndTask -> _getAllNotesAndTask.value = mapper.fromDomain(notesAndTask)
            }
        }
    }

    fun addNoteAndTaskItem(noteAndTodo: InNoteAndTask) {
        screenModelScope.launch(Dispatchers.IO) {
            add.invoke(mapper.toDomain(noteAndTodo))
        }
    }

    override fun sendUiEvent(event: UiEvent<NoteAndTask>) {
        when(event) {
            is UiEvent.Delete -> performUiEvent { delete(mapper.toDomain(event.data)) }
            is UiEvent.Insert -> performUiEvent { add(mapper.toDomain(event.data)) }
            else -> throw Exception("Not implemented")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}