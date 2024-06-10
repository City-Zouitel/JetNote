package city.zouitel.domain.repository

import city.zouitel.domain.model.NoteAndAudio
import kotlinx.coroutines.flow.Flow

interface NoteAndAudioRepository {

    val getAllNotesAndAudio: Flow<List<NoteAndAudio>>

    suspend fun addNoteAndAudio(noteAndAudio: NoteAndAudio)

    suspend fun updateNoteAndAudio(noteAndAudio: NoteAndAudio)

    suspend fun deleteNoteAndAudio(noteAndAudio: NoteAndAudio)
}