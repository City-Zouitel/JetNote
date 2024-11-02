package city.zouitel.media.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.media.mapper.MediaMapper
import city.zouitel.media.model.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MediaScreenModel(
    getAllMedias: MediaUseCase.GetAllMedias,
    private val addMedia: MediaUseCase.AddMedia,
    private val deleteMedia: MediaUseCase.DeleteMedia,
    private val mapper: MediaMapper
): ScreenModel, UiEventHandler<Media> {

    private val _allMedias: MutableStateFlow<List<Media>> = MutableStateFlow(
        emptyList()
    )

    val allMedias: StateFlow<List<Media>> = _allMedias.stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            listOf()
        )

    init {
        performUiEvent {
            getAllMedias().collect { audios -> _allMedias.value = mapper.fromDomain(audios) }
        }
    }

    override fun sendUiEvent(event: UiEvent<Media>) {
        when(event) {
            is UiEvent.Delete -> performUiEvent { deleteMedia(mapper.toDomain(event.data)) }
            is UiEvent.Insert -> performUiEvent { addMedia(mapper.toDomain(event.data)) }
            else -> throw Exception("Not Implemented!")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}