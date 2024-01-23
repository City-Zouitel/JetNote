package city.zouitel.domain.repository

import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag
import kotlinx.coroutines.flow.Flow

interface NoteAndTagRepository {

    val getAllNotesAndTags: Flow<List<OutNoteAndTag>>

    suspend fun addNoteAndTag(noteAndTag: OutNoteAndTag)

    suspend fun deleteNoteAndTag(noteAndTag: OutNoteAndTag)
}