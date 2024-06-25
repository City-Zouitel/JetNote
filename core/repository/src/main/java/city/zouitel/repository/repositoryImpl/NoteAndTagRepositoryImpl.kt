package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag
import city.zouitel.domain.repository.NoteAndTagRepository
import city.zouitel.repository.datasource.NoteAndTagDataSource
import city.zouitel.repository.mapper.NoteAndTagMapper
import city.zouitel.repository.model.NoteAndTag as InNoteAndTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndTagRepositoryImpl(
    private val dataSource: NoteAndTagDataSource,
    private val mapper: NoteAndTagMapper
): NoteAndTagRepository {
    override val getAllNotesAndTags: Flow<List<OutNoteAndTag>>
        get() = dataSource.getAllNotesAndTags.map { notesAndTag -> mapper.toDomain(notesAndTag) }

    override suspend fun addNoteAndTag(noteAndTag: OutNoteAndTag) {
        dataSource.addNoteAndTag(mapper.fromDomain(noteAndTag))
    }

    override suspend fun deleteNoteAndTag(noteAndTag: OutNoteAndTag) {
        dataSource.deleteNoteAndTag(mapper.fromDomain(noteAndTag))
    }
}