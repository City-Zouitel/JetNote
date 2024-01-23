package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.NoteAndLink as OutNoteAndLink
import city.zouitel.domain.repository.NoteAndLinkRepository
import city.zouitel.repository.datasource.NoteAndLinkDataSource
import city.zouitel.repository.mapper.NoteAndLinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndLinkRepositoryImpl /*@Inject*/ constructor(
    private val dataSource: NoteAndLinkDataSource,
    private val mapper: NoteAndLinkMapper
): NoteAndLinkRepository {
    override val getAllNotesAndLinks: Flow<List<OutNoteAndLink>>
        get() = dataSource.getAllNotesAndLinks.map { list ->
            list.map { noteAndLink ->
                mapper.toDomain(noteAndLink)
            }
        }

    override suspend fun addNoteAndLink(noteAndLink: OutNoteAndLink) {
        dataSource.addNoteAndLink(mapper.toRepository(noteAndLink))
    }

    override suspend fun deleteNoteAndLink(noteAndLink: OutNoteAndLink) {
        dataSource.deleteNoteAndLink(mapper.toRepository(noteAndLink))
    }
}