package com.example.mobile.ui.add_and_edit.bottom_bar

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.datastore.DataStore
import com.example.local.model.Note
import com.example.mobile.cons.*
import com.example.mobile.fp.getMaterialColor
import com.example.mobile.icons.ADD_CIRCLE_ICON
import com.example.mobile.icons.BELL_ICON
import com.example.mobile.ui.AdaptingRow
import com.example.mobile.ui.add_and_edit.ColorsRow
import com.example.mobile.ui.coloration.listOfBackgroundColors
import com.example.mobile.ui.coloration.listOfTextColors
import com.example.mobile.ui.settings_screen.makeSound

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddEditBottomBar(
    note: Note,
    navController: NavController,
    imageLaunch : ManagedActivityResultLauncher<String, Uri?>,
    recordDialogState: MutableState<Boolean>,
    remindingDialogState: MutableState<Boolean>,
    backgroundColorState: MutableState<Int>,
    textColorState: MutableState<Int>,
    priorityColorState: MutableState<String>,
    notePriority : MutableState<String>,
    titleFieldState : MutableState<String?>,
    descriptionFieldState : MutableState<String?>,
    isTitleFieldSelected : MutableState<Boolean>,
    isDescriptionFieldSelected : MutableState<Boolean>,
    isCollapsed: BottomSheetScaffoldState
) {

    val showOptionsMenu = remember { mutableStateOf(false) }
    val ctx = LocalContext.current
    val thereIsSoundEffect = DataStore(ctx).thereIsSoundEffect.collectAsState(false)

    Column {
        Row {
            AdaptingRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(getMaterialColor(SURFACE))
                    .height(50.dp)
                    .padding(end = if (isCollapsed.bottomSheetState.isCollapsed) 80.dp else 0.dp)
            ) {

                Icon(
                    painter = painterResource(id = ADD_CIRCLE_ICON),
                    contentDescription = null,
                    tint = contentColorFor(backgroundColor = getMaterialColor(SURFACE_VARIANT)),
                    modifier = Modifier
                        .clickable {
                            showOptionsMenu.value = !showOptionsMenu.value
                            Unit.makeSound.invoke(ctx, FOCUS_NAVIGATION, thereIsSoundEffect.value)
                        }
                )

                Icon(
                    painterResource(BELL_ICON), contentDescription =null,
                    tint = contentColorFor(backgroundColor = getMaterialColor(SURFACE_VARIANT)),
                    modifier = Modifier.clickable {
                        remindingDialogState.value = !remindingDialogState.value
                        Unit.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                    })

                // undo
                UndoRedo(
                    titleFieldState = titleFieldState,
                    descriptionFieldState = descriptionFieldState,
                    isTitleFieldSelected = isTitleFieldSelected,
                    isDescriptionFieldSelected = isDescriptionFieldSelected,
                )
            }
        }

        // more options menu.
        Plus(
            isShow = showOptionsMenu,
            note = note,
            navController = navController,
            imageLaunch = imageLaunch,
            recordDialogState = recordDialogState,
            priorityColorState = priorityColorState
        )

        // row of background colors.
        ColorsRow(backgroundColorState, listOfBackgroundColors)

        // row of text colors.
        ColorsRow(textColorState, listOfTextColors)

    }

}





