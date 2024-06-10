package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.NoteAndAudio
import city.zouitel.domain.repository.NoteAndAudioRepository
import city.zouitel.repository.datasource.NoteAndAudioDataSource
import city.zouitel.repository.mapper.NoteAndAudioMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndAudioRepositoryImpl(
    private val dataSource: NoteAndAudioDataSource,
    private val mapper: NoteAndAudioMapper
): NoteAndAudioRepository {
    override val getAllNotesAndAudio: Flow<List<NoteAndAudio>>
        get() = dataSource.getAllNotesAndAudio.map { list ->
            list.map { noteAndAudio ->
                mapper.toDomain(noteAndAudio)
            }
        }

    override suspend fun addNoteAndAudio(noteAndAudio: NoteAndAudio) {
        dataSource.addNoteAndAudio(mapper.toRepository(noteAndAudio))
    }

    override suspend fun updateNoteAndAudio(noteAndAudio: NoteAndAudio) {
        dataSource.updateNoteAndAudio(mapper.toRepository(noteAndAudio))
    }

    override suspend fun deleteNoteAndAudio(noteAndAudio: NoteAndAudio) {
        dataSource.deleteNoteAndAudio(mapper.toRepository(noteAndAudio))
    }
}