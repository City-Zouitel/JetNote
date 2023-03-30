package com.example.mobile.top_action_bar

import androidx.compose.foundation.clickable
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.Icons.DASHBOARD_ICON
import com.example.common_ui.Icons.LIST_VIEW_ICON_1
import com.example.graph.sound
import com.example.common_ui.DataStoreVM

@Composable
internal fun Layout(
    dataStoreVM: DataStoreVM = hiltViewModel()
) {
    val ctx = LocalContext.current
    val currentLayout  = remember(dataStoreVM, dataStoreVM::getLayout).collectAsState()
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    Icon(
        painter = if (currentLayout.value == "LIST") painterResource(id = DASHBOARD_ICON) else painterResource(id = LIST_VIEW_ICON_1),
        contentDescription = null,
        modifier = Modifier.clickable {
            sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
//            scope.launch {
//                dataStore?.saveLayout(!collectAsState!!)
//            }
            dataStoreVM.setLayout(
                if (currentLayout.value == "GRID") "LIST" else "GRID"
            )
        }
    )
}


