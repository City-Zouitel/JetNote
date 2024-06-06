package city.zouitel.tags.state

data class UiState(
    val currentId: Long = 0L,
    val currentColor: Int = 0,
    val isColorsDialog: Boolean = false,
    val currentLabel: String = ""
)
