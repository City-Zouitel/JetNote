package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndTagDao
import city.zouitel.database.mapper.NoteAndTagMapper
import city.zouitel.repository.datasource.NoteAndTagDataSource
import city.zouitel.repository.model.NoteAndTag as OutNoteAndTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndTagDataSourceImpl /*@Inject*/ constructor(
    private val noteAndTagDao: NoteAndTagDao,
    private val noteAndTagMapper: NoteAndTagMapper
): NoteAndTagDataSource {
    override val getAllNotesAndTags: Flow<List<OutNoteAndTag>>
        get() = noteAndTagDao.getAllNotesAndTags().map { list ->
            list.map {
                noteAndTagMapper.readOnly(it)
            }
        }

    override suspend fun addNoteAndTag(noteAndTag: OutNoteAndTag) {
        noteAndTagDao.addNoteAndTag(noteAndTagMapper.toLocal(noteAndTag))
    }

    override suspend fun deleteNoteAndTag(noteAndTag: OutNoteAndTag) {
        noteAndTagDao.deleteNoteAndTag(noteAndTagMapper.toLocal(noteAndTag))
    }
}