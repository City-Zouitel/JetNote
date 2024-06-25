package city.zouitel.audios.ui.component

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.audios.mapper.NoteAndAudioMapper
import city.zouitel.audios.model.NoteAndAudio
import city.zouitel.domain.usecase.NoteAndAudioUseCase
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
): ScreenModel {

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
        screenModelScope.launch(Dispatchers.IO) {
            getAllNotesAndAudios.invoke().collect { notesAndAudio ->
                _allNotesAndAudios.value = mapper.fromDomain(notesAndAudio)
            }
        }
    }

    fun deleteNoteAndAudio(noteAndAudio: NoteAndAudio) {
        screenModelScope.launch(Dispatchers.IO) {
            deleteNoteAndAudio.invoke(mapper.toDomain(noteAndAudio))
        }
    }
}