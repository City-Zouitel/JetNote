package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndTagDao
import city.zouitel.database.mapper.NoteAndTagMapper
import city.zouitel.repository.datasource.NoteAndTagDataSource
import city.zouitel.repository.model.NoteAndTag as OutNoteAndTag
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndTagDataSourceImpl /*@Inject*/ constructor(
    private val dao: NoteAndTagDao,
    private val mapper: NoteAndTagMapper
): NoteAndTagDataSource {
    override val getAllNotesAndTags: Flow<List<OutNoteAndTag>>
        get() = dao.getAllNotesAndTags().map { list ->
            list.map {
                mapper.readOnly(it)
            }
        }

    override suspend fun addNoteAndTag(noteAndTag: OutNoteAndTag) {
        dao.addNoteAndTag(mapper.toLocal(noteAndTag))
    }

    override suspend fun deleteNoteAndTag(noteAndTag: OutNoteAndTag) {
        dao.deleteNoteAndTag(mapper.toLocal(noteAndTag))
    }
}