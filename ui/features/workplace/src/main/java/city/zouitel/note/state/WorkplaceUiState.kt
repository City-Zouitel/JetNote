package city.zouitel.note.state

import city.zouitel.note.ui.utils.PriorityColorsList

internal data class WorkplaceUiState(
    val priority: String = PriorityColorsList.NON.priority,
    val textColor: Int = 0,
    val backgroundColor: Int = 0,
    val isTitleFieldFocused: Boolean = false,
    val isDescriptionFieldFocused: Boolean = false
)