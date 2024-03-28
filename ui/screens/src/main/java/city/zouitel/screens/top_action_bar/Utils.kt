package city.zouitel.screens.top_action_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import city.zouitel.screens.sound
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons
import city.zouitel.systemDesign.CommonPopupTip
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Open_Drawer(
    dataStoreVM: DataStoreVM = koinViewModel(),
    drawerState: DrawerState,
    ) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val scope = rememberCoroutineScope()
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    CommonPopupTip(message = "Navigation Drawer") {
        Icon(
            painterResource(Icons.MENU_BURGER_ICON),
            null,
            modifier = Modifier.combinedClickable(
                onLongClick = {
                    // To make vibration.
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    it.showAlignBottom()
                }
            ) {
                scope.launch {
                    sound.makeSound.invoke(context, Cons.KEY_CLICK, thereIsSoundEffect.value)
                    drawerState.open()
                }
            }
        )
    }
}