package city.zouitel.links.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.NoteAndLinkUseCase
import city.zouitel.links.mapper.NoteAndLinkMapper
import city.zouitel.links.model.NoteAndLink
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import city.zouitel.links.model.NoteAndLink as InNoteAndLink

class NoteAndLinkScreenModel(
    private val getAll: NoteAndLinkUseCase.GetAllNotesAndLinks,
    private val delete: NoteAndLinkUseCase.DeleteNoteAndLink,
    private val mapper: NoteAndLinkMapper
): ScreenModel, UiEventHandler<NoteAndLink> {

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
        performUiEvent {
            getAll().collect { notesAndLink ->
                _getAllNotesAndLinks.value = mapper.fromDomain(notesAndLink)
            }
        }
    }

    override fun sendUiEvent(event: UiEvent<NoteAndLink>) {
        when(event) {
            is UiEvent.Delete -> performUiEvent { delete(mapper.toDomain(event.data)) }
            else -> throw Exception("Not Implemented!")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}