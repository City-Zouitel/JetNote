package city.zouitel.tags.ui

import cafe.adriel.voyager.core.model.ScreenModel
import city.zouitel.domain.usecase.NoteAndTagUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.logic.withFlow
import city.zouitel.tags.mapper.NoteAndTagMapper
import city.zouitel.tags.model.NoteAndTag
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Suppress("DeferredResultUnused")
class NoteAndTagScreenModel(
    private val _observeAll_: NoteAndTagUseCase.ObserveAll,
    private val insert: NoteAndTagUseCase.Insert,
    private val delete: NoteAndTagUseCase.Delete,
    private val mapper: NoteAndTagMapper
): ScreenModel {

    private val _observeAll = MutableStateFlow<List<NoteAndTag>>(emptyList())
    val observeAll: StateFlow<List<NoteAndTag>>
        get() = _observeAll
            .withFlow(emptyList()) {
                withAsync {
                    _observeAll_().collect { _observeAll.value = mapper.fromDomain(it) }
                }
            }

    fun sendAction(act: Action) {
        when (act) {
            is Action.Insert<*> -> withAsync { insert(mapper.toDomain(act.data as NoteAndTag)) }
            is Action.DeleteById -> withAsync { delete(act.id as Long) }
            else -> throw Exception("Not implemented")
        }
    }
}