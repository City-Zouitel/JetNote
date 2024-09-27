package city.zouitel.screens.main_screen.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.screens.main_screen.EraseScreen
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonPopupTip

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BroomData() {
    val haptic = LocalHapticFeedback.current
    val navBottomSheet = LocalBottomSheetNavigator.current

    CommonPopupTip(message = "Wipe All Notes") { balloonWindow ->
        Icon(
            painterResource(CommonIcons.BROOM_ICON),
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
                    /*homeModel.updateErasing(true)*/
                    navBottomSheet.show(EraseScreen {})
                }
        )
    }
}