package city.zouitel.audio.state

internal data class ItemUiState(
    val displayName: String,
    val size: String,
    val onClick: () -> Unit
)