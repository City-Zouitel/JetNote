package city.zouitel.recoder.state

internal data class UiState(
    val isRecording: Boolean = false,
    val isPause: Boolean = false,
    val isPlaying: Boolean = false,
    val seconds: String = "00",
    val minutes: String = "00",
    val hours: String = "00"
)
