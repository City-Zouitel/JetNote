package city.zouitel.systemDesign

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
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
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Open_Drawer(
    dataStoreModel: DataStoreScreenModel,
    drawerState: DrawerState,
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val scope = rememberCoroutineScope()
    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()

    CommonPopupTip(message = "Navigation Drawer") {
        Icon(
            painterResource(CommonIcons.MENU_BURGER_ICON),
            null,
            modifier = Modifier.combinedClickable(
                onLongClick = {
                    // To make vibration.
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    it.showAlignBottom()
                }
            ) {
                scope.launch {
                    SoundEffect().makeSound.invoke(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                    drawerState.open()
                }
            }
        )
    }
}