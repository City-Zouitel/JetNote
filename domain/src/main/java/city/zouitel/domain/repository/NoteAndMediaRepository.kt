package city.zouitel.domain.repository

import city.zouitel.domain.model.NoteAndMedia
import kotlinx.coroutines.flow.Flow

interface NoteAndMediaRepository {

    val getAllNotesAndMedia: Flow<List<NoteAndMedia>>

    fun addNoteAndMedia(noteAndMedia: NoteAndMedia)

    fun updateNoteAndMedia(noteAndMedia: NoteAndMedia)

    fun deleteNoteAndMedia(noteAndMedia: NoteAndMedia)
}