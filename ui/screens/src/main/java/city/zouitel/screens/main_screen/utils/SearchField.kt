package city.zouitel.screens.main_screen.utils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.screens.utils.sound
import city.zouitel.systemDesign.CommonConstants
import city.zouitel.systemDesign.CommonIcons
import city.zouitel.systemDesign.CommonIcons.CROSS_SMALL_ICON
import city.zouitel.systemDesign.CommonTextField
import city.zouitel.systemDesign.DataStoreScreenModel

@Composable
internal fun SearchField(
    dataStoreModel: DataStoreScreenModel,
    mainModel: MainScreenModel
) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val keyboardManager = LocalFocusManager.current

    val thereIsSoundEffect = remember(dataStoreModel, dataStoreModel::isMute).collectAsState()
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
            placeholder = if (uiState.isHomeScreen) "Notes.." else "Archives..",
            imeAction = ImeAction.Search,
            keyboardActions = KeyboardActions {
                keyboardController?.hide()
                keyboardManager.clearFocus(true)
            }
        )
        if (uiState.searchTitle.isNotEmpty() || uiState.searchTag != null) {
            Icon(
                painter = painterResource(CROSS_SMALL_ICON),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .clickable {
                        sound.performSoundEffect(context, CommonConstants.KEY_INVALID, thereIsSoundEffect.value)
                        mainModel.updateSearchTitle("")
                        mainModel.updateSearchTag(null)
                        keyboardController?.hide()
                        keyboardManager.clearFocus(true)
                    }
            )
        }
    }
}