package city.zouitel.audios.state

internal data class SingleAudioUiState(
    val id: Long,
    val displayName: String,
    val size: String,
    val onClick: () -> Unit
)