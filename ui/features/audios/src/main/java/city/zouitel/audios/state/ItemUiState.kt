package city.zouitel.audios.state

internal data class ItemUiState(
    val displayName: String,
    val size: String,
    val onClick: () -> Unit
)