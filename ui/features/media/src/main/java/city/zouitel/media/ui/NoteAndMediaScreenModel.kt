package city.zouitel.media.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndMediaUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.media.mapper.NoteAndMediaMapper
import city.zouitel.media.model.NoteAndMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteAndMediaScreenModel(
    getAllNotesAndMedia: NoteAndMediaUseCase.GetAllNotesAndMedia,
    private val addNoteAndMedia: NoteAndMediaUseCase.AddNoteAndMedia,
    private val updateNoteAndMedia: NoteAndMediaUseCase.UpdateNoteAndMedia,
    private val deleteNoteAndMedia: NoteAndMediaUseCase.DeleteNoteAndMedia,
    private val mapper: NoteAndMediaMapper
): ScreenModel, UiEventHandler<NoteAndMedia> {

    private val _getAllNotesAndMedia: MutableStateFlow<List<NoteAndMedia>> = MutableStateFlow(
        emptyList()
    )

    val getAllNotesAndMedia: StateFlow<List<NoteAndMedia>> = _getAllNotesAndMedia.stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(),
        listOf()
    )

    init {
        performUiEvent {
            getAllNotesAndMedia().collect { notesAndMedia ->
                _getAllNotesAndMedia.value = mapper.fromDomain(notesAndMedia)
            }
        }
    }

    override fun sendUiEvent(event: UiEvent<NoteAndMedia>) {
        when(event) {
            is UiEvent.Insert -> performUiEvent { addNoteAndMedia(mapper.toDomain(event.data)) }
            is UiEvent.Delete -> performUiEvent { deleteNoteAndMedia(mapper.toDomain(event.data)) }
            else -> throw Exception("Not Implemented!")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}