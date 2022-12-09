package com.example.jetnote.ui.top_action_bar

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.jetnote.icons.DASHBOARD_ICON
import com.example.jetnote.icons.LIST_VIEW_ICON_1
import com.example.jetnote.ds.DataStore
import kotlinx.coroutines.launch

@Composable
internal fun Layout(
    dataStore: DataStore?
) {
    val collectAsState = dataStore?.getLayout?.collectAsState(true)?.value
    val scope = rememberCoroutineScope()
    Icon(
        painter = if (collectAsState == true) painterResource(id = DASHBOARD_ICON) else painterResource(id = LIST_VIEW_ICON_1),
        contentDescription = null,
        modifier = Modifier.clickable {
            scope.launch {
                dataStore?.saveLayout(!collectAsState!!)
            }
        }
    )
}


