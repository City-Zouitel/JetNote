package com.example.note.bottom_bar

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
import com.example.common_ui.*
import com.example.common_ui.Cons.FOCUS_NAVIGATION
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.Icons.ADD_CIRCLE_ICON
import com.example.common_ui.Icons.BELL_ICON
import com.example.common_ui.MatColors.Companion.SURFACE
import com.example.common_ui.MatColors.Companion.SURFACE_VARIANT
import com.example.datastore.DataStore
import com.example.local.model.Note
import com.example.note.ColorsRow

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

    val getMatColor = MatColors().getMaterialColor
    val sound = SoundEffect()

    Column {
        Row {
            AdaptingRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(getMatColor(SURFACE))
                    .height(50.dp)
                    .padding(end = if (isCollapsed.bottomSheetState.isCollapsed) 80.dp else 0.dp)
            ) {

                Icon(
                    painter = painterResource(id = ADD_CIRCLE_ICON),
                    contentDescription = null,
                    tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
                    modifier = Modifier
                        .clickable {
                            showOptionsMenu.value = !showOptionsMenu.value
                            sound.makeSound.invoke(ctx, FOCUS_NAVIGATION, thereIsSoundEffect.value)
                        }
                )

                Icon(
                    painterResource(BELL_ICON), contentDescription =null,
                    tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
                    modifier = Modifier.clickable {
                        remindingDialogState.value = !remindingDialogState.value
                        sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
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





