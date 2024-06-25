package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndLinkDao
import city.zouitel.database.mapper.NoteAndLinkMapper
import city.zouitel.repository.datasource.NoteAndLinkDataSource
import city.zouitel.repository.model.NoteAndLink as OutNoteAndLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndLinkDataSourceImpl(
    private val dao: NoteAndLinkDao,
    private val  mapper: NoteAndLinkMapper
): NoteAndLinkDataSource {
    override val getAllNotesAndLinks: Flow<List<OutNoteAndLink>>
        get() = dao.getAllNotesAndLinks().map { notesAndLink -> mapper.toRepo(notesAndLink) }

    override suspend fun addNoteAndLink(noteAndLink: OutNoteAndLink) {
        dao.addNoteAndLink(mapper.fromRepo(noteAndLink))
    }

    override suspend fun deleteNoteAndLink(noteAndLink: OutNoteAndLink) {
        dao.deleteNoteAndLink(mapper.fromRepo(noteAndLink))
    }
}