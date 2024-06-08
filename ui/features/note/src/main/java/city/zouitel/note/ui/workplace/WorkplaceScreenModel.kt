package city.zouitel.note.ui.workplace

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.note.state.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class WorkplaceScreenModel: ScreenModel {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(
        UiState()
    )
    internal var uiState: StateFlow<UiState> = _uiState
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            UiState()
        )

    fun updateRecorderDialog(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(recordedDialogState = value)
        }
    }

    fun updatePriority(property: String): WorkplaceScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(priority = property)
        }
        return this
    }

    fun updateBackgroundColor(color: Int): WorkplaceScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(backgroundColor = color)
        }
        return this
    }

    fun updateTextColor(color: Int): WorkplaceScreenModel {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(textColor = color)
        }
        return this
    }

    fun updateRemindingDialog(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(remindingDialogState = value)
        }
    }

    fun updateRemindingValue(value: Long) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(reminding = value)
        }
    }

    fun updateTitleFieldFocused(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isTitleFieldFocused = value)
        }
    }

    fun updateDescriptionFieldFocused(value: Boolean) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(isDescriptionFieldFocused = value)
        }
    }
}