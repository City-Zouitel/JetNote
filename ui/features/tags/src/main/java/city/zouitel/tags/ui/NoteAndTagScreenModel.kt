package city.zouitel.tags.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.tags.mapper.NoteAndTagMapper
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteAndTagScreenModel(
    private val getAll: NoteAndTagUseCase.GetAllNotesAndTags,
    private val add: NoteAndTagUseCase.AddNoteAndTag,
    private val delete: NoteAndTagUseCase.DeleteNoteAndTag,
    private val mapper: NoteAndTagMapper
): ScreenModel {

    private val _getAllNotesAndTags = MutableStateFlow<List<InNoteAndTag>>(emptyList())
    val getAllNotesAndTags: StateFlow<List<InNoteAndTag>>
        get() = _getAllNotesAndTags
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        screenModelScope.launch(Dispatchers.IO) {
            getAll.invoke().collect { notesAndTag ->
                _getAllNotesAndTags.value = mapper.fromDomain(notesAndTag)
            }
        }
    }
    fun addNoteAndTag(noteAndTag: InNoteAndTag) {
        screenModelScope.launch(Dispatchers.IO) {
            add.invoke(mapper.toDomain(noteAndTag))
        }
    }
    fun deleteNoteAndTag(noteAndTag: InNoteAndTag) {
        screenModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(noteAndTag))
        }
    }
}