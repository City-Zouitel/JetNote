package com.example.graph.top_action_bar.selection_bars

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.common_ui.Icons
import com.example.common_ui.PopupTip
import com.example.note.model.Data

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectionCount(
    selectionState: MutableState<Boolean>?,
    selectedNotes: SnapshotStateList<Data>?
) {
    PopupTip(message = "Cancel") {
        Icon(
            painter = painterResource(id = Icons.CROSS_ICON),
            contentDescription = null,
            modifier = Modifier.combinedClickable(
                onLongClick = {
                    it.showAlignBottom()
                }
            ) {
                selectionState?.value = false
                selectedNotes?.clear()
            }
        )
    }
    // number of selected notes.
    Text(
        text = selectedNotes?.count().toString(),
        fontSize = 24.sp,
        modifier = Modifier.padding(5.dp)
    )
}
