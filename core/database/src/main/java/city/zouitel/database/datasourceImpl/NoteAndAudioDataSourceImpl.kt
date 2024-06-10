package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndAudioDao
import city.zouitel.database.mapper.NoteAndAudioMapper
import city.zouitel.repository.datasource.NoteAndAudioDataSource
import city.zouitel.repository.model.NoteAndAudio
import city.zouitel.repository.model.NoteAndAudio as OutNoteAndAudio
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndAudioDataSourceImpl(
    private val dao: NoteAndAudioDao,
    private val mapper: NoteAndAudioMapper
): NoteAndAudioDataSource {
    override val getAllNotesAndAudio: Flow<List<OutNoteAndAudio>>
        get() = dao.getAllNotesAndAudios().map { list ->
            list.map { noteAndAudio ->
                mapper.readOnly(noteAndAudio)
            }
        }

    override suspend fun addNoteAndAudio(noteAndAudio: OutNoteAndAudio) {
        dao.addNoteAndAudio(mapper.toLocal(noteAndAudio))
    }

    override suspend fun updateNoteAndAudio(noteAndAudio: NoteAndAudio) {
        dao.updateNoteAndAudio(mapper.toLocal(noteAndAudio))
    }

    override suspend fun deleteNoteAndAudio(noteAndAudio: OutNoteAndAudio) {
        dao.deleteNoteAndAudio(mapper.toLocal(noteAndAudio))
    }
}