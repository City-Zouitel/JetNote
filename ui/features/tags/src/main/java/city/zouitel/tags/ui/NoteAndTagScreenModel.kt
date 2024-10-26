package city.zouitel.tags.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.tags.mapper.NoteAndTagMapper
import city.zouitel.tags.model.NoteAndTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag

class NoteAndTagScreenModel(
    private val getAll: NoteAndTagUseCase.GetAllNotesAndTags,
    private val add: NoteAndTagUseCase.AddNoteAndTag,
    private val delete: NoteAndTagUseCase.DeleteNoteAndTag,
    private val mapper: NoteAndTagMapper
): ScreenModel, UiEventHandler<NoteAndTag> {

    private val _getAllNotesAndTags = MutableStateFlow<List<InNoteAndTag>>(emptyList())
    val getAllNotesAndTags: StateFlow<List<InNoteAndTag>>
        get() = _getAllNotesAndTags
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            )

    init {
        performUiEvent {
            getAll.invoke().collect { notesAndTag ->
                _getAllNotesAndTags.value = mapper.fromDomain(notesAndTag)
            }
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }

    override fun sendUiEvent(event: UiEvent<NoteAndTag>) {
        when (event) {
            is UiEvent.Insert -> performUiEvent { add.invoke(mapper.toDomain(event.data)) }
            is UiEvent.Delete -> performUiEvent { runCatching { delete.invoke(mapper.toDomain(event.data)) } }
            is UiEvent.Update -> throw Exception("Not implemented")
        }
    }
}