package city.zouitel.tags.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.domain.utils.Action
import city.zouitel.domain.utils.withAsync
import city.zouitel.logic.withFlow
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.model.Tag
import city.zouitel.tags.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import city.zouitel.tags.model.Tag as InTag

@Suppress("DeferredResultUnused")
class TagScreenModel(
    private val _observeAll_: TagUseCase.ObserveAll,
    private val insert: TagUseCase.Insert,
    private val deleteById: TagUseCase.DeleteById,
    private val mapper: TagMapper
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState
            .withFlow(UiState())

    private val _observeAll: MutableStateFlow<List<Tag>> = MutableStateFlow(emptyList())
    val observeAll: StateFlow<List<InTag>>
        get() = _observeAll
            .withFlow(emptyList()) {
                withAsync {
                    _observeAll_().collect { tags -> _observeAll.value = mapper.fromDomain(tags) }
                }
            }

    fun sendAction(act: Action) {
        when (act) {
            is Action.Insert<*> -> withAsync { insert(mapper.toDomain(act.data as Tag)) }
            is Action.DeleteById -> withAsync { deleteById(act.id as Long) }
            else -> throw Exception("Not implemented")
        }
    }

    fun updateId(id: Long = 0L): TagScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(currentId = id)
        }
        return this
    }

    fun updateColor(color: Int = 0x0000): TagScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(currentColor = color)
        }
        return this
    }

    fun updateColorDialogState(isShow: Boolean = false): TagScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isColorsDialog = isShow)
        }
        return this
    }

    fun updateLabel(label: String = ""): TagScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(currentLabel = label)
        }
        return this
    }
}