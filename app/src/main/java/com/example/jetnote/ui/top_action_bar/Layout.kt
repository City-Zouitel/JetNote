package com.example.jetnote.ui.top_action_bar

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import com.example.jetnote.cons.KEY_CLICK
import com.example.jetnote.icons.DASHBOARD_ICON
import com.example.jetnote.icons.LIST_VIEW_ICON_1
import com.example.jetnote.ds.DataStore
import com.example.jetnote.ui.settings_screen.makeSound
import kotlinx.coroutines.launch

@Composable
internal fun Layout(
    dataStore: DataStore?
) {
    val collectAsState = dataStore?.getLayout?.collectAsState(true)?.value
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)

    Icon(
        painter = if (collectAsState == true) painterResource(id = DASHBOARD_ICON) else painterResource(id = LIST_VIEW_ICON_1),
        contentDescription = null,
        modifier = Modifier.clickable {
            Unit.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
            scope.launch {
                dataStore?.saveLayout(!collectAsState!!)
            }
        }
    )
}


