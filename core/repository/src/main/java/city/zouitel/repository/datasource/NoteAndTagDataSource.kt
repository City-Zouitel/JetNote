package city.zouitel.repository.datasource

import city.zouitel.repository.model.NoteAndTag
import kotlinx.coroutines.flow.Flow

interface NoteAndTagDataSource {

    val getAllNotesAndTags: Flow<List<NoteAndTag>>

    suspend fun addNoteAndTag(noteAndTag: NoteAndTag)

    suspend fun deleteNoteAndTag(noteAndTag: NoteAndTag)
}