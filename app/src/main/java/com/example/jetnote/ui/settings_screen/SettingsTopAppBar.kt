package com.example.jetnote.ui.settings_screen

import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.jetnote.icons.MENU_BURGER_ICON
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopAppBar(
    drawerState: DrawerState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = { Text("Setting") },
        scrollBehavior = topAppBarScrollBehavior,
        navigationIcon = {
            Icon(
                painterResource(MENU_BURGER_ICON),
                null,
                modifier = Modifier.clickable {
                    scope.launch {
                        drawerState.open()
                    }
                }
            )
        }
    )
}