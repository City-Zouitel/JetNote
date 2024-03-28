package city.zouitel.screens.top_action_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.systemDesign.Icons
import city.zouitel.systemDesign.CommonPopupTip

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BroomData(
    confirmationDialogState: MutableState<Boolean>?,
    ) {
    val haptic = LocalHapticFeedback.current

    CommonPopupTip(message = "Wipe All Notes") { balloonWindow ->
        Icon(
            painterResource(Icons.BROOM_ICON),
            null,
            modifier = Modifier
                .padding(5.dp)
                .combinedClickable(
                    onLongClick = {
                        // To make vibration.
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        balloonWindow.showAlignBottom()
                    }
                ) {
                    confirmationDialogState?.value = true
                }
        )
    }
}