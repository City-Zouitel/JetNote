package city.zouitel.media.ui

import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.domain.usecase.MediaUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.logic.withFlow
import city.zouitel.media.mapper.MediaMapper
import city.zouitel.media.model.Media
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("DeferredResultUnused")
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
            withAsync {
                _observeAll_().collect { _observeAll.value = mapper.fromDomain(it) }
            }
        }

    private val _observeByUid: MutableStateFlow<List<Media>> = MutableStateFlow(emptyList())
    val observeByUid: StateFlow<List<Media>> = _observeByUid.withFlow(emptyList())

    fun initializeUid(uid: String) {
        withAsync {
            _observeByUid_(uid).collect { _observeByUid.value = mapper.fromDomain(it) }
        }
    }

    fun sendAction(act: Action) {
        when(act) {
            is Action.Insert<*> -> withAsync { insert(mapper.toDomain(act.data as Media)) }
            is Action.DeleteById -> withAsync { deleteById(act.id as Long) }
            else -> throw NotImplementedError("Action not implemented: $act")
        }
    }
}