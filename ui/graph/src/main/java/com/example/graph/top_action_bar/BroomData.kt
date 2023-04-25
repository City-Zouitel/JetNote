package com.example.graph.top_action_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import com.example.common_ui.Icons
import com.example.mobile.home_screen.PopupTip

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BroomData(
    confirmationDialogState: MutableState<Boolean>?,
    ) {
    val haptic = LocalHapticFeedback.current

    PopupTip(message = "...") { balloonWindow ->
        Icon(
            painterResource(Icons.BROOM_ICON),
            null,
            modifier = Modifier
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