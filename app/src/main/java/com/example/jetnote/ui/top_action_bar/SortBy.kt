package com.example.jetnote.ui.top_action_bar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.jetnote.cons.*
import com.example.jetnote.ds.DataStore
import com.example.jetnote.icons.*
import kotlinx.coroutines.launch

@Composable
internal fun SortBy(
    isShow: MutableState<Boolean>?,
    dataStore: DataStore?
) {
    val scope = rememberCoroutineScope()
    val currentSortIcon = when(dataStore?.getOrder?.collectAsState(null)?.value) {
        ORDER_BY_NAME -> SORT_ALPHA_DOWN_ICON
        ORDER_BY_OLDEST -> SORT_AMOUNT_UP_ICON
        ORDER_BY_NEWEST -> SORT_AMOUNT_DOWN_ICON
        ORDER_BY_PRIORITY -> INTERLINING_ICON
        ORDER_BY_REMINDER -> SORT_NUMERIC_ICON
        else -> SORT_ICON
    }
    Icon(
        painterResource(currentSortIcon),
        contentDescription = null,
        modifier = Modifier
            .size(24.dp)
            .clickable {
                isShow?.value = true
            }
    )

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
                    isShow.value = false
                    scope.launch {
                        dataStore?.saveOrder(ORDER_BY_DEFAULT)
                    }
                }
            )
            DropdownMenuItem(
                text = { Text(NEWEST_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painter = painterResource(id = SORT_AMOUNT_DOWN_ICON), null ) },
                onClick = {
                    isShow.value = false
                    scope.launch {
                        dataStore?.saveOrder(ORDER_BY_NEWEST)
                    }
                }
            )
            DropdownMenuItem(
                text = { Text(OLDEST_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painter = painterResource(id = SORT_AMOUNT_UP_ICON), null ) },
                onClick = {
                    isShow.value = false
                    scope.launch {
                        dataStore?.saveOrder(ORDER_BY_OLDEST)
                    }
                }
            )
            DropdownMenuItem(
                text = { Text(NAME_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painter = painterResource(id = SORT_ALPHA_DOWN_ICON), null ) },
                onClick = {
                    isShow.value = false
                    scope.launch {
                        dataStore?.saveOrder(ORDER_BY_NAME)
                    }
                }
            )
            DropdownMenuItem(
                text = { Text(REMINDING_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painterResource(SORT_NUMERIC_ICON), null ) },
                onClick = {
                    isShow.value = false
                    scope.launch {
                        dataStore?.saveOrder(ORDER_BY_REMINDER)
                    }
                }
            )
            DropdownMenuItem(
                text = { Text(PRIORITY_ORDER, fontSize = 17.sp) },
                leadingIcon = { Icon(painter = painterResource(id = INTERLINING_ICON), null ) },
                onClick = {
                    isShow.value = false
                    scope.launch {
                        dataStore?.saveOrder(ORDER_BY_PRIORITY)
                    }
                }
            )
        }
    }
}