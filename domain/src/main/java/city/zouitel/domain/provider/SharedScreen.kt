package city.zouitel.domain.provider

import cafe.adriel.voyager.core.registry.ScreenProvider

sealed class SharedScreen: ScreenProvider {
    data class MainScreen(val isHome: Boolean): SharedScreen()
    data class Workplace(
        val uid: String,
        val isNew: Boolean,
        val title: String?,
        val description: String?,
        val backgroundColor: Int,
        val textColor: Int,
        val priority: Int
    ): SharedScreen()
    data object Assistant: SharedScreen()
    data class AudioList(val uid: String): SharedScreen()
    data class Reminder(
        val uid: String,
        val title: String?,
        val message: String?
    ): SharedScreen()
    data class Tags(val uid: String): SharedScreen()
    data class Tasks(val uid: String): SharedScreen()
}