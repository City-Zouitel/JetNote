package city.zouitel.screens.navigation_drawer

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import city.zouitel.screens.state.NavigationDrawerUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NavigationDrawerScreenModel: ScreenModel {
    private val _uiState: MutableStateFlow<NavigationDrawerUiState> = MutableStateFlow(
        NavigationDrawerUiState()
    )

    internal val uiState: StateFlow<NavigationDrawerUiState> = _uiState
        .stateIn(
            screenModelScope,
            SharingStarted.WhileSubscribed(),
            NavigationDrawerUiState()
        )

    fun updateCurrentScreen(screen: NoteScreens) {
        screenModelScope.launch {
            _uiState.value = _uiState.value.copy(currentScreen = screen)
        }
    }
}