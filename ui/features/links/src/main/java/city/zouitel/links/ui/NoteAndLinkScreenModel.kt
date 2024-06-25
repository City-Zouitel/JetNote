package city.zouitel.links.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndLinkUseCase
import city.zouitel.links.mapper.NoteAndLinkMapper
import city.zouitel.links.model.NoteAndLink
import city.zouitel.links.model.NoteAndLink as InNoteAndLink
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteAndLinkScreenModel(
    private val getAll: NoteAndLinkUseCase.GetAllNotesAndLinks,
    private val delete: NoteAndLinkUseCase.DeleteNoteAndLink,
    private val mapper: NoteAndLinkMapper
): ScreenModel {

    private val _getAllNotesAndLinks: MutableStateFlow<List<NoteAndLink>> = MutableStateFlow(
        emptyList()
    )
    val getAllNotesAndLinks: StateFlow<List<InNoteAndLink>> = _getAllNotesAndLinks
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                listOf()
            )

    init {
        screenModelScope.launch(Dispatchers.IO) {
            getAll.invoke().collect { notesAndLink ->
                _getAllNotesAndLinks.value = mapper.fromDomain(notesAndLink)
            }
        }
    }

    fun deleteNoteAndLink(noteAndLink: InNoteAndLink) {
        screenModelScope.launch(Dispatchers.IO) {
            delete.invoke(mapper.toDomain(noteAndLink))
        }
    }
}