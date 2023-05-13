package com.example.graph.top_action_bar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.AdaptingRow
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.DataStoreVM
import com.example.common_ui.Icons.BROOM_ICON
import com.example.common_ui.Icons.MENU_BURGER_ICON
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.example.graph.getMaterialColor
import com.example.graph.sound
import com.example.local.model.Label
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun NoteTopAppBar(
    searchNoteTitle: MutableState<String>,
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    thisHomeScreen: Boolean,
    confirmationDialogState: MutableState<Boolean>?,
    expandedSortMenuState: MutableState<Boolean>?,
    searchScreen: String,
    label: MutableState<Label>?
) {

    TopAppBar(
        navigationIcon = {
            Row {
                AnimatedVisibility(searchNoteTitle.value.isEmpty() && label?.value == Label()) {
                    AdaptingRow(
                        Modifier.padding(start = 10.dp, end = 10.dp),
                    ) {
                        Open_Drawer(drawerState = drawerState)
                    }
                }
            }
        },
        title = {
            SearchField(
                title = searchNoteTitle,
                placeholder = searchScreen,
                label = label
            )
        },
        actions = {
            AnimatedVisibility(visible = searchNoteTitle.value.isEmpty() && label?.value == Label()) {
                AdaptingRow(
                    Modifier.padding(start = 10.dp, end = 10.dp),
                ) {
                    thisHomeScreen.let {
                        if (it) {
                            SortBy(
                                isShow = expandedSortMenuState,
                            )
                        } else {
                          BroomData(confirmationDialogState = confirmationDialogState)
                        }
                    }

                    Spacer(modifier = Modifier.width(5.dp))

                    Layout()
                }
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = getMaterialColor(SURFACE)
        ),
        scrollBehavior = scrollBehavior
    )
}