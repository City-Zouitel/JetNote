package city.zouitel.note.state

import android.net.Uri
import city.zouitel.systemDesign.CommonConstants

internal data class UiState(
    val priority: String = CommonConstants.NONE,
    val remindingDialogState: Boolean = false,
    val reminding: Long = 0L,
    val recordedDialogState: Boolean = false,
    val photo: Uri? = null, // TODO:
    val isTitleFieldFocused: Boolean = false,
    val isDescriptionFieldFocused: Boolean = false,
    val isFocused: Boolean = false, // TODO:  
    val backgroundColor: Int = 0,
    val textColor: Int = 0
)
