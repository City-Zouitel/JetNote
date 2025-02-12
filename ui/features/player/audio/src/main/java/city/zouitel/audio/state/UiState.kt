package city.zouitel.audio.state

import city.zouitel.audio.model.Record
import city.zouitel.systemDesign.CommonIcons

internal data class UiState(
    var isPlaying: Boolean = false,
    val icon: Int = CommonIcons.PLAY_ICON_24,
    val displayName: String = "",
    val progress: Float = 0F,
    val amplitudes: List<Int> = (0..100).toList().map { 1 },
    val playingRecord: Record? = null,
    val isLoading: Boolean = false,
    val currentPath: String = ""
)