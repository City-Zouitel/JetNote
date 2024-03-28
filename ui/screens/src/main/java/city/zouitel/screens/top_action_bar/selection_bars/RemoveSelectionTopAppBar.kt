package city.zouitel.screens.top_action_bar.selection_bars

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import city.zouitel.screens.sound
import city.zouitel.note.DataScreenModel
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.CommonRow
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons
import city.zouitel.systemDesign.CommonPopupTip
import org.koin.androidx.compose.koinViewModel

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun RemoveSelectionTopAppBar(
    dataStoreVM: DataStoreVM = koinViewModel(),
    dataScreenModel: DataScreenModel,
    trashSelectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Data>?
) {
    val context = LocalContext.current
    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    TopAppBar(
        navigationIcon = {
            // wipe notes.
            CommonPopupTip(message = "Wipe Notes") {
                Icon(
                    painter = painterResource(id = Icons.REMOVE_ICON),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(7.dp)
                        .combinedClickable(
                            onLongClick = {
                                it.showAlignBottom()
                            }
                        ) {
                            sound.makeSound.invoke(context, Cons.KEY_CLICK, thereIsSoundEffect.value)
                            selectedNotes?.forEach {
                                dataScreenModel.deleteData(it)
                            }
                            selectedNotes?.clear()
                            trashSelectionState?.value = false
                        }
                )
            }

        },
        title = {},
        actions = {
            CommonRow(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
                SelectionCount(selectionState = trashSelectionState, selectedNotes = selectedNotes)
            }
        }
    )
}