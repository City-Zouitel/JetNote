package city.zouitel.note.state

import android.net.Uri
import city.zouitel.note.utils.PriorityColorsList

internal data class WorkplaceUiState(
    val priority: String = PriorityColorsList.NON.priority,
    val textColor: Int = 0,
    val backgroundColor: Int = 0,
    val reminding: Long = 0L,
    val remindingDialogState: Boolean = false,
    val recordedDialogState: Boolean = false,
    val isTitleFieldFocused: Boolean = false,
    val isDescriptionFieldFocused: Boolean = false,
    val photo: Uri? = null, // TODO:
    val isFocused: Boolean = false // TODO:
) {
  init {
//      println(priority)
  }
}