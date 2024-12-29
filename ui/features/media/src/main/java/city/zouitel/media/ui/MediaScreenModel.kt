package city.zouitel.media.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.logic.asLogicFlow
import city.zouitel.logic.events.UiEvents
import city.zouitel.logic.events.UiEventsHandler
import city.zouitel.media.mapper.MediaMapper
import city.zouitel.media.model.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MediaScreenModel(
    _observeAll_: MediaUseCase.ObserveAll,
    private val _observeByUid_: MediaUseCase.ObserveByUid,
    private val insert: MediaUseCase.Insert,
    private val deleteById: MediaUseCase.DeleteById,
    private val mapper: MediaMapper
): ScreenModel, UiEventsHandler {

    private val _observeAll: MutableStateFlow<List<Media>> = MutableStateFlow(emptyList())
    val observeAll: StateFlow<List<Media>> = _observeAll
        .asLogicFlow(emptyList()) {
            performUiEvent {
                _observeAll_().collect { _observeAll.value = mapper.fromDomain(it) }
            }
        }

    private val _observeByUid: MutableStateFlow<List<Media>> = MutableStateFlow(emptyList())
    val observeByUid: StateFlow<List<Media>> = _observeByUid.asLogicFlow(emptyList())

    fun initializeUid(uid: String) {
        performUiEvent {
            _observeByUid_(uid).collect { _observeByUid.value = mapper.fromDomain(it) }
        }
    }

    override fun sendUiEvent(event: UiEvents) {
        when(event) {
            is UiEvents.Delete<*> -> performUiEvent { deleteById(event.data as Long) }
            is UiEvents.Insert<*> -> performUiEvent { insert(mapper.toDomain(event.data as Media)) }
            else -> throw Exception("Not Implemented!")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}