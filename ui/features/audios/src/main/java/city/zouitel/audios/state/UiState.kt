package city.zouitel.audios.state

import city.zouitel.systemDesign.CommonIcons

internal data class UiState(
    var isPlaying: Boolean = false,
    val icon: Int = CommonIcons.PLAY_ICON_24,
    val displayName: String = "",
    val progress: Float = 0F,
    val amplitudes: List<Int> = (0..100).toList().map { 5 }
)