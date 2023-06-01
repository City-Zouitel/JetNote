package com.example.graph.top_action_bar

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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.common_ui.*
import com.example.graph.sound
import com.example.graph.top_action_bar.selection_bars.SelectionCount
import com.example.local.model.Note
import com.example.note.DataViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TrashSelectionTopAppBar(
    dataStoreVM: DataStoreVM = hiltViewModel(),
    dataViewModel: DataViewModel = hiltViewModel(),
    trashSelectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Note>?,

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
                                dataViewModel.deleteNote(it)
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