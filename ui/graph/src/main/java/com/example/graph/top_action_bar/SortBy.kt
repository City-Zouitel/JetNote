package com.example.graph.top_action_bar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Cons.DEFAULT_ORDER
import com.example.common_ui.Cons.FOCUS_NAVIGATION
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.Cons.NAME_ORDER
import com.example.common_ui.Cons.NEWEST_ORDER
import com.example.common_ui.Cons.OLDEST_ORDER
import com.example.common_ui.Cons.BY_NAME
import com.example.common_ui.Cons.ORDER_BY_NEWEST
import com.example.common_ui.Cons.ORDER_BY_OLDEST
import com.example.common_ui.Cons.ORDER_BY_PRIORITY
import com.example.common_ui.Cons.ORDER_BY_REMINDER
import com.example.common_ui.Cons.PRIORITY_ORDER
import com.example.common_ui.Cons.REMINDING_ORDER
import com.example.common_ui.DataStoreVM
import com.example.common_ui.Icons.INTERLINING_ICON
import com.example.common_ui.Icons.SORT_ALPHA_DOWN_ICON
import com.example.common_ui.Icons.SORT_AMOUNT_DOWN_ICON
import com.example.common_ui.Icons.SORT_AMOUNT_UP_ICON
import com.example.common_ui.Icons.SORT_ICON
import com.example.common_ui.Icons.SORT_NUMERIC_ICON
import com.example.graph.sound
import com.example.common_ui.PopupTip

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SortBy(
    dataStoreVM: DataStoreVM = hiltViewModel(),
    isShow: MutableState<Boolean>?
) {
    val ctx = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()
    val haptic = LocalHapticFeedback.current

    val currentSortIcon = when(
        remember(dataStoreVM, dataStoreVM::getOrdination).collectAsState().value
    ) {
        BY_NAME -> SORT_ALPHA_DOWN_ICON
        ORDER_BY_OLDEST -> SORT_AMOUNT_UP_ICON
        ORDER_BY_NEWEST -> SORT_AMOUNT_DOWN_ICON
        ORDER_BY_PRIORITY -> INTERLINING_ICON
        ORDER_BY_REMINDER -> SORT_NUMERIC_ICON
        else -> SORT_ICON
    }

    PopupTip(message = "Ordination.") {
        Icon(
            painterResource(currentSortIcon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .combinedClickable(
                    onLongClick = {
                        // To make vibration.
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        it.showAlignBottom()
                    }
                ) {
                    sound.makeSound.invoke(ctx, FOCUS_NAVIGATION, thereIsSoundEffect.value)
                    isShow?.value = true
                }
        )
    }

    if (isShow != null) {
        DropdownMenu(
            expanded = isShow.value,
            onDismissRequest = {
                isShow.value = false
            },
            properties = PopupProperties(
                focusable = true
            )
        ) {
            DropdownMenuItem(
                text = { Text(DEFAULT_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(
                    painter = painterResource(id = SORT_ICON),
                    null,
                    modifier = Modifier.size(24.dp)
                ) },
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    isShow.value = false
//                    scope.launch {
//                        dataStore?.saveOrder(Cons.ORDINATION)
//                    }
                    dataStoreVM.setOrdination(com.example.common_ui.Cons.BY_ID)
                }
            )
            DropdownMenuItem(
                text = { Text(NEWEST_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painter = painterResource(id = SORT_AMOUNT_DOWN_ICON), null ) },
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    isShow.value = false
//                    scope.launch {
//                        dataStore?.saveOrder(ORDER_BY_NEWEST)
//                    }
                    dataStoreVM.setOrdination(com.example.common_ui.Cons.ORDER_BY_NEWEST)
                }
            )
            DropdownMenuItem(
                text = { Text(OLDEST_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painter = painterResource(id = SORT_AMOUNT_UP_ICON), null ) },
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    isShow.value = false
//                    scope.launch {
//                        dataStore?.saveOrder(ORDER_BY_OLDEST)
//                    }
                    dataStoreVM.setOrdination(ORDER_BY_OLDEST)
                }
            )
            DropdownMenuItem(
                text = { Text(NAME_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painter = painterResource(id = SORT_ALPHA_DOWN_ICON), null ) },
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    isShow.value = false
//                    scope.launch {
//                        dataStore?.saveOrder(BY_NAME)
//                    }
                    dataStoreVM.setOrdination(BY_NAME)
                }
            )
            DropdownMenuItem(
                text = { Text(REMINDING_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painterResource(SORT_NUMERIC_ICON), null ) },
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    isShow.value = false
//                    scope.launch {
//                        dataStore?.saveOrder(ORDER_BY_REMINDER)
//                    }
                    dataStoreVM.setOrdination(ORDER_BY_REMINDER)
                }
            )
            DropdownMenuItem(
                text = { Text(PRIORITY_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painter = painterResource(id = INTERLINING_ICON), null ) },
                onClick = {
                    sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    isShow.value = false
//                    scope.launch {
//                        dataStore?.saveOrder(ORDER_BY_PRIORITY)
//                    }
                    dataStoreVM.setOrdination(ORDER_BY_PRIORITY)
                }
            )
        }
    }
}