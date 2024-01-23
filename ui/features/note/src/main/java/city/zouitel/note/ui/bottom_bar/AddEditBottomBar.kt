package city.zouitel.note.ui.bottom_bar

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.navigation.NavController
import city.zouitel.note.ColorsRow
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.AdaptingRow
import city.zouitel.systemDesign.Cons.FOCUS_NAVIGATION
import city.zouitel.systemDesign.Cons.KEY_CLICK
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons.ADD_CIRCLE_ICON
import city.zouitel.systemDesign.Icons.BELL_ICON
import city.zouitel.systemDesign.MaterialColors
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE
import city.zouitel.systemDesign.MaterialColors.Companion.SURFACE_VARIANT
import city.zouitel.systemDesign.PopupTip
import city.zouitel.systemDesign.SoundEffect
import city.zouitel.systemDesign.listOfBackgroundColors
import city.zouitel.systemDesign.listOfTextColors
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class
)
@Composable
fun AddEditBottomBar(
    dataStoreVM: DataStoreVM = koinViewModel(),
    note: Data,
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





