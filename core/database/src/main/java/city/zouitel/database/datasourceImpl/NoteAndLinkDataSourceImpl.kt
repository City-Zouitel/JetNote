package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndLinkDao
import city.zouitel.database.mapper.NoteAndLinkMapper
import city.zouitel.repository.datasource.NoteAndLinkDataSource
import city.zouitel.repository.model.NoteAndLink as OutNoteAndLink
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndLinkDataSourceImpl /*@Inject*/ constructor(
    private val noteAndLinkDao: NoteAndLinkDao,
    private val  noteAndLinkMapper: NoteAndLinkMapper
): NoteAndLinkDataSource {
    override val getAllNotesAndLinks: Flow<List<OutNoteAndLink>>
        get() = noteAndLinkDao.getAllNotesAndLinks().map { list ->
            list.map {
                noteAndLinkMapper.readOnly(it)
            }
        }

    override suspend fun addNoteAndLink(noteAndLink: OutNoteAndLink) {
        noteAndLinkDao.addNoteAndLink(noteAndLinkMapper.toLocal(noteAndLink))
    }

    override suspend fun deleteNoteAndLink(noteAndLink: OutNoteAndLink) {
        noteAndLinkDao.deleteNoteAndLink(noteAndLinkMapper.toLocal(noteAndLink))
    }
}