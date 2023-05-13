package com.example.graph.top_action_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Cons
import com.example.common_ui.DataStoreVM
import com.example.common_ui.Icons
import com.example.common_ui.PopupTip
import com.example.graph.sound
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
internal fun Open_Drawer(
    dataStoreVM: DataStoreVM = hiltViewModel(),
    drawerState: DrawerState,
    ) {
    val ctx = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val scope = rememberCoroutineScope()
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    PopupTip(message = "Navigation Drawer") {
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
                    sound.makeSound.invoke(ctx, Cons.KEY_CLICK, thereIsSoundEffect.value)
                    drawerState.open()
                }
            }
        )
    }
}