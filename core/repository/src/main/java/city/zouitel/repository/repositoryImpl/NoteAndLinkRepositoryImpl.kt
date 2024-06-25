package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.NoteAndLink as OutNoteAndLink
import city.zouitel.domain.repository.NoteAndLinkRepository
import city.zouitel.repository.datasource.NoteAndLinkDataSource
import city.zouitel.repository.mapper.NoteAndLinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndLinkRepositoryImpl(
    private val dataSource: NoteAndLinkDataSource,
    private val mapper: NoteAndLinkMapper
): NoteAndLinkRepository {
    override val getAllNotesAndLinks: Flow<List<OutNoteAndLink>>
        get() = dataSource.getAllNotesAndLinks.map { notesAndLink -> mapper.toDomain(notesAndLink) }

    override suspend fun addNoteAndLink(noteAndLink: OutNoteAndLink) {
        dataSource.addNoteAndLink(mapper.fromDomain(noteAndLink))
    }

    override suspend fun deleteNoteAndLink(noteAndLink: OutNoteAndLink) {
        dataSource.deleteNoteAndLink(mapper.fromDomain(noteAndLink))
    }
}