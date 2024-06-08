package city.zouitel.screens.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import city.zouitel.note.model.Data
import city.zouitel.tags.model.Tag

internal data class UiState(
    val searchTitle: String = "",
    val searchTag: Tag? = null,
    val expandedSortMenu: Boolean = false,
    val isProcessing: Boolean = false,
    val isSelection: Boolean = false,
    val selectedNotes: SnapshotStateList<Data> = mutableStateListOf(),
    val isErase: Boolean = false,
    val isEraseDialog: Boolean = false,
    val isHomeScreen: Boolean = true
) {

    fun addSelectedNote(note: Data): UiState {
        selectedNotes.add(note)
        return this
    }
}