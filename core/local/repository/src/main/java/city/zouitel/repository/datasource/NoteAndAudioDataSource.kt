package city.zouitel.repository.datasource

import city.zouitel.repository.model.NoteAndAudio
import kotlinx.coroutines.flow.Flow

interface NoteAndAudioDataSource {

    val getAllNotesAndAudio: Flow<List<NoteAndAudio>>

    suspend fun addNoteAndAudio(noteAndAudio: NoteAndAudio)

    suspend fun updateNoteAndAudio(noteAndAudio: NoteAndAudio)

    suspend fun deleteNoteAndAudio(noteAndAudio: NoteAndAudio)
}