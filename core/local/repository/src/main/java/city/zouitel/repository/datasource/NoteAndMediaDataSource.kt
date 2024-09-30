package city.zouitel.repository.datasource

import city.zouitel.repository.model.NoteAndMedia
import kotlinx.coroutines.flow.Flow

interface NoteAndMediaDataSource {

    val getAllNotesAndMedia: Flow<List<NoteAndMedia>>

    fun addNoteAndMedia(noteAndMedia: NoteAndMedia)

    fun updateNoteAndMedia(noteAndMedia: NoteAndMedia)

    fun deleteNoteAndMedia(noteAndMedia: NoteAndMedia)
}