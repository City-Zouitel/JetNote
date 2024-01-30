package city.zouitel.navigation.top_action_bar.selection_bars

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
import city.zouitel.navigation.sound
import city.zouitel.note.DataViewModel
import city.zouitel.note.model.Data
import city.zouitel.systemDesign.AdaptingRow
import city.zouitel.systemDesign.Cons
import city.zouitel.systemDesign.DataStoreVM
import city.zouitel.systemDesign.Icons
import city.zouitel.systemDesign.PopupTip
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TrashSelectionTopAppBar(
    dataStoreVM: DataStoreVM = koinViewModel(),
    dataViewModel: DataViewModel = koinViewModel(),
    trashSelectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Data>?,

    ) {
    val ctx = LocalContext.current

    val thereIsSoundEffect = remember(dataStoreVM, dataStoreVM::getSound).collectAsState()

    TopAppBar(
        navigationIcon = {
            // wipe notes.
            PopupTip(message = "Wipe Notes") {
                Icon(
                    painter = painterResource(id = Icons.TRASH_ICON),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(7.dp)
                        .combinedClickable(
                            onLongClick = {
                                it.showAlignBottom()
                            }
                        ) {
                            sound.makeSound.invoke(ctx, Cons.KEY_CLICK, thereIsSoundEffect.value)
                            selectedNotes?.forEach {
                                dataViewModel.deleteData(it)
                            }
                            selectedNotes?.clear()
                            trashSelectionState?.value = false
                        }
                )
            }

        },
        title = {},
        actions = {
            AdaptingRow(
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            ) {
                SelectionCount(selectionState = trashSelectionState, selectedNotes = selectedNotes)
            }
        }
    )
}