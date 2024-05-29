package city.zouitel.note.ui.workplace

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.note.state.UiState
import kotlinx.coroutines.launch

class WorkplaceScreenModel: ScreenModel {

    internal var uiState by mutableStateOf(UiState())
        private set

    fun updateRecorderDialog(value: Boolean) {
        screenModelScope.launch {
            uiState = uiState.copy(recordedDialogState = value)
        }
    }

    fun updatePriority(value: String): WorkplaceScreenModel {
        screenModelScope.launch {
            uiState = uiState.copy(priority = value)
        }
        return this
    }

    fun updateBackgroundColor(value: Int): WorkplaceScreenModel {
        screenModelScope.launch {
            uiState = uiState.copy(backgroundColor = value)
        }
        return this
    }

    fun updateTextColor(value: Int): WorkplaceScreenModel {
        screenModelScope.launch {
            uiState = uiState.copy(textColor = value)
        }
        return this
    }

    fun updateRemindingDialog(value: Boolean) {
        screenModelScope.launch {
            uiState = uiState.copy(remindingDialogState = value)
        }
    }

    fun updateRemindingValue(value: Long) {
        screenModelScope.launch {
            uiState = uiState.copy(reminding = value)
        }
    }

    fun updateTitleFieldFocused(value: Boolean) {
        screenModelScope.launch {
            uiState = uiState.copy(isTitleFieldFocused = value)
        }
    }

    fun updateDescriptionFieldFocused(value: Boolean) {
        screenModelScope.launch {
            uiState = uiState.copy(isDescriptionFieldFocused = value)
        }
    }
}