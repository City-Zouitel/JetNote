package city.zouitel.tags.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.tags.mapper.NoteAndTagMapper
import city.zouitel.tags.model.NoteAndTag as InNoteAndTag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class NoteAndTagViewModel(
    private val getAll: NoteAndTagUseCase.GetAllNotesAndTags,
    private val add: NoteAndTagUseCase.AddNoteAndTag,
    private val delete: NoteAndTagUseCase.DeleteNoteAndTag,
    private val mapper: NoteAndTagMapper
): ViewModel() {

    private val _getAllNotesAndTags = MutableStateFlow<List<InNoteAndTag>>(emptyList())
    val getAllNotesAndTags: StateFlow<List<InNoteAndTag>>
        get() = _getAllNotesAndTags
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAll.invoke().collect { list ->
                _getAllNotesAndTags.value = list.map { noteAndTag -> mapper.toView(noteAndTag) }
            }
        }
    }
    fun addNoteAndTag(noteAndTag: InNoteAndTag) {
        viewModelScope.launch(Dispatchers.IO) {
            add.invoke(mapper.toDomain(noteAndTag))
        }
    }
    fun deleteNoteAndTag(noteAndTag: InNoteAndTag) {
        viewModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(noteAndTag))
        }
    }
}