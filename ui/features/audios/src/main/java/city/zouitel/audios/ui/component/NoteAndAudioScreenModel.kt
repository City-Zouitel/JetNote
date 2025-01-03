package city.zouitel.audios.ui.component

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.audios.mapper.NoteAndAudioMapper
import city.zouitel.audios.model.NoteAndAudio
import city.zouitel.domain.usecase.NoteAndAudioUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteAndAudioScreenModel(
    getAllNotesAndAudios: NoteAndAudioUseCase.GetAllNotesAndAudios,
    private val deleteNoteAndAudio: NoteAndAudioUseCase.DeleteNoteAndAudio,
    private val mapper: NoteAndAudioMapper
): ScreenModel, UiEventHandler<NoteAndAudio> {

    private val _allNotesAndAudios: MutableStateFlow<List<NoteAndAudio>> = MutableStateFlow(
        emptyList()
    )
    val allNoteAndAudio: StateFlow<List<NoteAndAudio>> = _allNotesAndAudios
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    init {
        performUiEvent {
            getAllNotesAndAudios().collect { notesAndAudio ->
                _allNotesAndAudios.value = mapper.fromDomain(notesAndAudio)
            }
        }
    }

    override fun sendUiEvent(event: UiEvent<NoteAndAudio>) {
        when(event) {
            is UiEvent.Delete -> performUiEvent {
                deleteNoteAndAudio(mapper.toDomain(event.data))
            }
            else -> throw Exception("Not Implemented!")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}