package city.zouitel.screens.main_screen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import city.zouitel.screens.sound
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonPopupTip
import city.zouitel.systemDesign.CommonTextField
import city.zouitel.systemDesign.DataStoreScreenModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SortBy(
    dataStoreModel: DataStoreScreenModel,
    mainModel: MainScreenModel
) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()

    val currentSortIcon = when(
        remember(dataStoreModel, dataStoreModel::getOrdination).collectAsState().value
    ) {
        CommonConstants.BY_NAME -> CommonIcons.SORT_ALPHA_DOWN_ICON
        CommonConstants.ORDER_BY_OLDEST -> CommonIcons.SORT_AMOUNT_UP_ICON
        CommonConstants.ORDER_BY_NEWEST -> CommonIcons.SORT_AMOUNT_DOWN_ICON
        CommonConstants.ORDER_BY_PRIORITY -> CommonIcons.INTERLINING_ICON
        CommonConstants.ORDER_BY_REMINDER -> CommonIcons.SORT_NUMERIC_ICON
        else -> CommonIcons.SORT_ICON
    }

    CommonPopupTip(message = "Sorting") {
        Icon(
            painterResource(currentSortIcon),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp)
                .combinedClickable(
                    onLongClick = {
                        // To make vibration.
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        it.showAlignBottom()
                    }
                ) {
                    sound.makeSound.invoke(
                        context,
                        CommonConstants.FOCUS_NAVIGATION,
                        thereIsSoundEffect.value
                    )
                    mainModel.updateExpandedSortMenu(true)
                }
        )
    }

    DropdownMenu(
        expanded = uiState.expandedSortMenu,
        onDismissRequest = {
            mainModel.updateExpandedSortMenu(false)
        },
        properties = PopupProperties(
            focusable = true
        )
    ) {
        DropdownMenuItem(
            text = { Text(CommonConstants.DEFAULT_ORDER, fontSize = 17.sp) },
            leadingIcon = { Icon(
                painter = painterResource(id = CommonIcons.SORT_ICON),
                null,
                modifier = Modifier.size(24.dp)
            ) },
            onClick = {
                sound.makeSound.invoke(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                mainModel.updateExpandedSortMenu(false)
                dataStoreModel.setOrdination(CommonConstants.BY_ID)
            }
        )
        DropdownMenuItem(
            text = { Text(CommonConstants.NEWEST_ORDER, fontSize = 17.sp) },
            leadingIcon = { Icon(painter = painterResource(id = CommonIcons.SORT_AMOUNT_DOWN_ICON), null ) },
            onClick = {
                sound.makeSound.invoke(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                mainModel.updateExpandedSortMenu(false)
                dataStoreModel.setOrdination(CommonConstants.ORDER_BY_NEWEST)
            }
        )
        DropdownMenuItem(
            text = { Text(CommonConstants.OLDEST_ORDER, fontSize = 17.sp) },
            leadingIcon = { Icon(painter = painterResource(id = CommonIcons.SORT_AMOUNT_UP_ICON), null ) },
            onClick = {
                sound.makeSound.invoke(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                mainModel.updateExpandedSortMenu(false)
                dataStoreModel.setOrdination(CommonConstants.ORDER_BY_OLDEST)
            }
        )
        DropdownMenuItem(
            text = { Text(CommonConstants.NAME_ORDER, fontSize = 17.sp) },
            leadingIcon = { Icon(painter = painterResource(id = CommonIcons.SORT_ALPHA_DOWN_ICON), null ) },
            onClick = {
                sound.makeSound.invoke(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                mainModel.updateExpandedSortMenu(false)
                dataStoreModel.setOrdination(CommonConstants.BY_NAME)
            }
        )
        DropdownMenuItem(
            text = { Text(CommonConstants.REMINDING_ORDER, fontSize = 17.sp) },
            leadingIcon = { Icon(painterResource(CommonIcons.SORT_NUMERIC_ICON), null ) },
            onClick = {
                sound.makeSound.invoke(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                mainModel.updateExpandedSortMenu(false)
                dataStoreModel.setOrdination(CommonConstants.ORDER_BY_REMINDER)
            }
        )
        DropdownMenuItem(
            text = { Text(CommonConstants.PRIORITY_ORDER, fontSize = 17.sp) },
            leadingIcon = { Icon(painter = painterResource(id = CommonIcons.INTERLINING_ICON), null ) },
            onClick = {
                sound.makeSound.invoke(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                mainModel.updateExpandedSortMenu(false)
                dataStoreModel.setOrdination(CommonConstants.ORDER_BY_PRIORITY)
            }
        )
    }
}

@Composable
internal fun SearchField(
    dataStoreModel: DataStoreScreenModel,
    mainModel: MainScreenModel
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardManager = LocalFocusManager.current

    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::getSound).collectAsState()
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()

    LaunchedEffect(Unit) {
        mainModel.updateSearchTitle("")
    }

    Row {
        Icon(
            modifier = Modifier.padding(top = 20.dp),
            painter = painterResource(id = CommonIcons.CIRCLE_ICON_18),
            contentDescription = null,
            tint = uiState.searchTag?.color?.let { Color(it) } ?: Color.Transparent
        )
        CommonTextField(
            value = uiState.searchTitle,
            onValueChange = { mainModel.updateSearchTitle(it) },
            receiver = {},
            modifier = Modifier.fillMaxWidth(.9f),
            placeholder = if (uiState.isHomeScreen) "Notes.." else "Removes..",
            imeAction = ImeAction.Search,
            keyboardActions = KeyboardActions {
                keyboardController?.hide()
                keyboardManager.clearFocus(true)
            }
        )
        if (uiState.searchTitle.isNotEmpty() || uiState.searchTag != null) {
            Icon(
                painter = painterResource(id = CommonIcons.CROSS_ICON),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clickable {
                        sound.makeSound.invoke(
                            context,
                            CommonConstants.KEY_INVALID,
                            thereIsSoundEffect.value
                        )
                        mainModel.updateSearchTitle("")
                        mainModel.updateSearchTag(null)
                        keyboardController?.hide()
                        keyboardManager.clearFocus(true)
                    }
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Layout(datastoreModel: DataStoreScreenModel) {
    val context = LocalContext.current
    val haptic = LocalHapticFeedback.current

    val currentLayout = remember(datastoreModel, datastoreModel::getLayout).collectAsState()
    val thereIsSoundEffect = remember(datastoreModel, datastoreModel::getSound).collectAsState()

    CommonPopupTip(message = if (currentLayout.value == CommonConstants.LIST) "Grade Layout" else "List Layout") {
        Icon(
            painter = if (currentLayout.value == CommonConstants.LIST) painterResource(id = CommonIcons.DASHBOARD_ICON)
            else painterResource(
                id = CommonIcons.LIST_VIEW_ICON_1
            ),
            contentDescription = null,
            modifier = Modifier.combinedClickable(
                onLongClick = {
                    // To make vibration.
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                    it.showAlignBottom()
                }
            ) {
                sound.makeSound.invoke(context, CommonConstants.KEY_CLICK, thereIsSoundEffect.value)
                datastoreModel.setLayout(
                    if (currentLayout.value == CommonConstants.GRID) CommonConstants.LIST else CommonConstants.GRID
                )
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun BroomData(homeModel: MainScreenModel) {
    val haptic = LocalHapticFeedback.current
    val navBottomSheet = LocalBottomSheetNavigator.current

    CommonPopupTip(message = "Wipe All Notes") { balloonWindow ->
        Icon(
            painterResource(CommonIcons.BROOM_ICON),
            null,
            modifier = Modifier
                .padding(5.dp)
                .combinedClickable(
                    onLongClick = {
                        // To make vibration.
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        balloonWindow.showAlignBottom()
                    }
                ) {
                    /*homeModel.updateErasing(true)*/
                    navBottomSheet.show(EraseScreen {})
                }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SelectionsCount(
    mainModel: MainScreenModel
) {
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()

    CommonPopupTip(message = "Cancel") {
        Icon(
            painter = painterResource(id = CommonIcons.CROSS_ICON),
            contentDescription = null,
            modifier = Modifier.combinedClickable(
                onLongClick = {
                    it.showAlignBottom()
                }
            ) {
                mainModel.updateSelection(false)
                mainModel.clearSelectionNotes()
            }
        )
    }
    // number of selected notes.
    Text(
        text = uiState.selectedNotes.count().toString(),
        fontSize = 24.sp,
        modifier = Modifier.padding(5.dp)
    )
}