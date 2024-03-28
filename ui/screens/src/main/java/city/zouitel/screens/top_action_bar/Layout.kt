package city.zouitel.screens.top_action_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import city.zouitel.screens.sound
import city.zouitel.systemDesign.Cons.GRID
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.Cons.LIST
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.DASHBOARD_ICON
import city.zouitel.systemDesign.Icons.LIST_VIEW_ICON_1
import city.zouitel.systemDesign.CommonPopupTip
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Layout(
    dataStoreVM: DataStoreVM = koinViewModel()
) {
    val ctx = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val currentLayout = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    CommonPopupTip(message = if (currentLayout.value == LIST) "Grade Layout" else "List Layout") {
        Icon(
            painter = if (currentLayout.value == LIST) painterResource(id = DASHBOARD_ICON)
            else painterResource(
                id = LIST_VIEW_ICON_1
            ),
            contentDescription = null,
            modifier = Modifier.combinedClickable(
                onLongClick = {
                    // To make vibration.
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    it.showAlignBottom()
                }
            ) {
                sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                dataStoreVM.setLayout(
                    if (currentLayout.value == GRID) LIST else GRID
                )
            }
        )
    }
}


