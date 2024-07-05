package city.zouitel.media.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndAudioUseCase
import city.zouitel.domain.usecase.NoteAndMediaUseCase
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
): ScreenModel {

    private val _getAllNotesAndMedia: MutableStateFlow<List<NoteAndMedia>> = MutableStateFlow(
        emptyList()
    )

    val getAllNotesAndMedia: StateFlow<List<NoteAndMedia>> = _getAllNotesAndMedia.stateIn(
        screenModelScope,
        SharingStarted.WhileSubscribed(),
        listOf()
    )

    init {
        screenModelScope.launch {
            getAllNotesAndMedia.invoke().collect { notesAndMedia ->
                _getAllNotesAndMedia.value = mapper.fromDomain(notesAndMedia)
            }
        }
    }

    fun addNoteAndMedia(noteAndMedia: NoteAndMedia) {
        screenModelScope.launch(Dispatchers.IO) {
            addNoteAndMedia.invoke(mapper.toDomain(noteAndMedia))
        }
    }

    fun updateNoteAndMedia(noteAndMedia: NoteAndMedia) {
        screenModelScope.launch {
            updateNoteAndMedia.invoke(mapper.toDomain(noteAndMedia))
        }
    }

    fun deleteNoteAndMedia(noteAndMedia: NoteAndMedia) {
        screenModelScope.launch(Dispatchers.IO) {
            deleteNoteAndMedia.invoke(mapper.toDomain(noteAndMedia))
        }
    }
}