package city.zouitel.links.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import city.zouitel.domain.usecase.NoteAndLinkUseCase
import city.zouitel.links.mapper.NoteAndLinkMapper
import city.zouitel.links.model.NoteAndLink as InNoteAndLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteAndLinkVM(
    private val getAll: NoteAndLinkUseCase.GetAllNotesAndLinks,
    private val delete: NoteAndLinkUseCase.DeleteNoteAndLink,
    private val mapper: NoteAndLinkMapper
): ViewModel() {

    private val _getAllNotesAndLinks = MutableStateFlow<List<InNoteAndLink>>(emptyList())
    val getAllNotesAndLinks: StateFlow<List<InNoteAndLink>>
        get() = _getAllNotesAndLinks
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getAll.invoke().collect { list ->
                _getAllNotesAndLinks.value = list.map { noteAndLink -> mapper.toView(noteAndLink) }
            }
        }
    }

    fun deleteNoteAndLink(noteAndLink: InNoteAndLink) {
        viewModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(noteAndLink))
        }
    }
}