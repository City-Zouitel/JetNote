package city.zouitel.screens.state

import city.zouitel.screens.navigation_drawer.NoteScreens
import city.zouitel.screens.navigation_drawer.NoteScreens.MAIN_SCREEN

internal data class NavigationDrawerUiState(
    val currentScreen: NoteScreens = MAIN_SCREEN
)
