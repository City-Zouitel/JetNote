package city.zouitel.screens.state

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import city.zouitel.note.model.Data
import city.zouitel.tags.model.Tag

internal data class MainScreenUiState(
    val searchTitle: String = "",
    val searchTag: Tag? = null,
    val expandedSortMenu: Boolean = false,
    val isProcessing: Boolean = false,
    val isSelection: Boolean = false,
    val isErase: Boolean = false,
    val isEraseDialog: Boolean = false,
    val isHomeScreen: Boolean = true,
    val isOptionsDialog: Boolean = false,
    val selectedNotes: SnapshotStateList<Data> = mutableStateListOf(),
    val selectedNote: Data? = null
) {

    fun addSelectedNote(note: Data): MainScreenUiState {
        selectedNotes.add(note)
        return this
    }
}