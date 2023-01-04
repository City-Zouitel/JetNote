package com.example.jetnote.ui.about_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.jetnote.icons.MENU_BURGER_ICON
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    drawerState: DrawerState,
    topAppBarScrollBehavior: TopAppBarScrollBehavior,
    title: String
) {
    val scope = rememberCoroutineScope()
    TopAppBar(
        title = { Text(title, fontSize = 22.sp,modifier = Modifier.padding(start = 15.dp)) },
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