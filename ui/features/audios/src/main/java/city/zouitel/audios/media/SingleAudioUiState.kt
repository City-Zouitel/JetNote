package city.zouitel.audios.media

data class SingleAudioUiState(
    val id: String,
    val displayName: String,
    val size: String,
    val onClick: () -> Unit
)

fun LocalAudio.toUiState(
    onClick: () -> Unit
) = SingleAudioUiState(
    id = id,
    displayName = name,
    size = duration.formatAsAudioDuration + " | " + size.formatAsFileSize,
    onClick = onClick
)