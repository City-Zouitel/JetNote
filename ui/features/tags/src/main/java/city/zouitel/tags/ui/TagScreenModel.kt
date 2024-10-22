package city.zouitel.tags.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.logic.UiEvent
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.state.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import city.zouitel.tags.model.Tag as InTag

class TagScreenModel(
    getAllTags: TagUseCase.GetAllTags,
    private val add: TagUseCase.AddTag,
    private val update: TagUseCase.UpdateTag,
    private val delete: TagUseCase.DeleteTag,
    private val mapper: TagMapper
): ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                UiState()
            )

    private val _getAllTags = MutableStateFlow<List<InTag>>(emptyList())
    val getAllLTags: StateFlow<List<InTag>> = _getAllTags.stateIn(screenModelScope, SharingStarted.WhileSubscribed(), listOf())

    init {
        screenModelScope.launch(Dispatchers.IO) {
            getAllTags.invoke().collect { tags -> _getAllTags.value = mapper.fromDomain(tags) }
        }
    }


    fun sendEvent(event: UiEvent<InTag>) {
        when(event) {
            is UiEvent.Insert -> {
                screenModelScope.launch(Dispatchers.IO) {
                    add.invoke(mapper.toDomain(event.data))
                }
            }
            is UiEvent.Delete -> {
                screenModelScope.launch(Dispatchers.IO) {
                    delete.invoke(mapper.toDomain(event.data))
                }
            }
            is UiEvent.Update -> {
                screenModelScope.launch(Dispatchers.IO) {
                    update.invoke(mapper.toDomain(event.data))
                }
            }
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