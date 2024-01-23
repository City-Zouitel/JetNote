package city.zouitel.domain.repository

import city.zouitel.domain.model.NoteAndLink as  OutNoteAndLink
import kotlinx.coroutines.flow.Flow

interface NoteAndLinkRepository {

    val getAllNotesAndLinks: Flow<List<OutNoteAndLink>>

    suspend fun addNoteAndLink(noteAndLink: OutNoteAndLink)

    suspend fun deleteNoteAndLink(noteAndLink: OutNoteAndLink)
}