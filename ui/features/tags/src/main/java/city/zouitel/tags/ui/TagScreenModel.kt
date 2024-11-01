package city.zouitel.tags.ui

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.domain.usecase.TagUseCase
import city.zouitel.logic.events.UiEvent
import city.zouitel.logic.events.UiEventHandler
import city.zouitel.tags.mapper.TagMapper
import city.zouitel.tags.model.Tag
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
): ScreenModel, UiEventHandler<Tag> {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState>
        get() = _uiState
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                UiState()
            )

    private val _getAllTags: MutableStateFlow<List<Tag>> = MutableStateFlow(emptyList())
    val getAllLTags: StateFlow<List<InTag>>
        get() = _getAllTags
            .stateIn(
                screenModelScope,
                SharingStarted.WhileSubscribed(),
                emptyList()
            )

    init {
        performUiEvent {
            getAllTags.invoke().collect { tags -> _getAllTags.value = mapper.fromDomain(tags) }
        }
    }

    override fun sendUiEvent(event: UiEvent<InTag>) {
        when (event) {
            is UiEvent.Insert -> performUiEvent { add.invoke(mapper.toDomain(event.data)) }
            is UiEvent.Delete -> performUiEvent { /*delete.invoke(mapper.toDomain(event.data))*/ }
            is UiEvent.Update -> performUiEvent { update.invoke(mapper.toDomain(event.data)) }
            else -> throw Exception("Not implemented")
        }
    }

    override fun performUiEvent(action: suspend () -> Unit) {
        screenModelScope.launch(Dispatchers.IO) { action.invoke() }
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