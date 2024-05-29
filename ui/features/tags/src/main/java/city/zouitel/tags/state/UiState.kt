package city.zouitel.tags.state

internal data class UiState(
    val currentId: Long = 0L,
    val currentColor: Int = 0,
    val colorsDialogState: Boolean = false
)
