package city.zouitel.audios.state

import city.zouitel.systemDesign.CommonIcons

internal data class AudioUiState(
    var isPlaying: Boolean = false,
    val icon: Int = CommonIcons.PAUSE_CIRCLE_FILLED_ICON_24,
    val progress: Float = 0F,
    val amplitudes: List<Int> = listOf(1,3,2,4,6,5,7,9,8,1,3,2,4,5,6,7,9,8,1,3,2,4,5,6,7,8,9,1,3,2,4,6,5,7,9,8,1,3,2,4,6,5,7,8,9,1,3,2,4,5,6,7,9,8)
)