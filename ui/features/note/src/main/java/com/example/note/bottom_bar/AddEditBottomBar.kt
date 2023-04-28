package com.example.note.bottom_bar

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.common_ui.*
import com.example.common_ui.Cons.FOCUS_NAVIGATION
import com.example.common_ui.Cons.KEY_CLICK
import com.example.common_ui.Icons.ADD_CIRCLE_ICON
import com.example.common_ui.Icons.BELL_ICON
import com.example.common_ui.MaterialColors.Companion.SURFACE
import com.example.common_ui.MaterialColors.Companion.SURFACE_VARIANT
import com.example.local.model.Note
import com.example.note.ColorsRow

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AddEditBottomBar(
    dataStoreVM: DataStoreVM = hiltViewModel(),
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

    val ctx = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val showOptionsMenu = remember { mutableStateOf(false) }
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    val getMatColor = MaterialColors().getMaterialColor
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

                PopupTip(message = "More Options") {
                    Icon(
                        painter = painterResource(id = ADD_CIRCLE_ICON),
                        contentDescription = null,
                        tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
                        modifier = Modifier
                            .combinedClickable(
                                onLongClick = {
                                    // To make vibration.
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    it.showAlignTop()
                                }
                            ) {
                                showOptionsMenu.value = !showOptionsMenu.value
                                sound.makeSound.invoke(
                                    ctx,
                                    FOCUS_NAVIGATION,
                                    thereIsSoundEffect.value
                                )
                            }
                    )
                }

                PopupTip(message = "Reminding") {
                    Icon(
                        painterResource(BELL_ICON), contentDescription = null,
                        tint = contentColorFor(backgroundColor = getMatColor(SURFACE_VARIANT)),
                        modifier = Modifier
                            .combinedClickable(
                                onLongClick = {
                                    // To make vibration.
                                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                                    it.showAlignTop()
                                }
                            ) {
                                remindingDialogState.value = !remindingDialogState.value
                                sound.makeSound.invoke(ctx, KEY_CLICK, thereIsSoundEffect.value)
                            }
                    )
                }

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
        ColorsRow(
            colorState = backgroundColorState,
            colors = listOfBackgroundColors
        )

        // row of text colors.
        ColorsRow(
            colorState = textColorState,
            colors = listOfTextColors
        )

    }

}





