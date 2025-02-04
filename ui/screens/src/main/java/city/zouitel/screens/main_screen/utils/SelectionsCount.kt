package city.zouitel.screens.main_screen.utils

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import city.zouitel.screens.main_screen.MainScreenModel
import city.zouitel.systemDesign.CommonIcons.CROSS_SMALL_ICON
import city.zouitel.systemDesign.CommonPopupTip

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SelectionsCount(
    mainModel: MainScreenModel
) {
    val uiState by remember(mainModel, mainModel::uiState).collectAsState()

    CommonPopupTip(message = "Cancel") {
        Icon(
            painter = painterResource(CROSS_SMALL_ICON),
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