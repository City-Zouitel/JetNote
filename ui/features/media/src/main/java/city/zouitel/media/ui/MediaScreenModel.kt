package city.zouitel.media.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.logic.withFlow
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
): ScreenModel {

    private val _observeAll: MutableStateFlow<List<Media>> = MutableStateFlow(emptyList())
    val observeAll: StateFlow<List<Media>> = _observeAll
        .withFlow(emptyList()) {
            act {
                _observeAll_().collect { _observeAll.value = mapper.fromDomain(it) }
            }
        }

    private val _observeByUid: MutableStateFlow<List<Media>> = MutableStateFlow(emptyList())
    val observeByUid: StateFlow<List<Media>> = _observeByUid.withFlow(emptyList())

    fun initializeUid(uid: String) {
        act {
            _observeByUid_(uid).collect { _observeByUid.value = mapper.fromDomain(it) }
        }
    }

    fun sendAction(action: Action) {
        when(action) {
            is Action.Delete<*> -> act { deleteById(action.data as Long) }
            is Action.Insert<*> -> act { insert(mapper.toDomain(action.data as Media)) }
            else -> throw Exception("Not Implemented!")
        }
    }

    private fun act(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
    }
}