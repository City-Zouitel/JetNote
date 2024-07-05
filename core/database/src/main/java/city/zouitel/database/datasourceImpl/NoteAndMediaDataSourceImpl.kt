package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndMediaDao
import city.zouitel.database.mapper.NoteAndMediaMapper
import city.zouitel.repository.datasource.NoteAndMediaDataSource
import city.zouitel.repository.model.NoteAndMedia
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndMediaDataSourceImpl(
    private val dao: NoteAndMediaDao,
    private val mapper: NoteAndMediaMapper
): NoteAndMediaDataSource {
    override val getAllNotesAndMedia: Flow<List<NoteAndMedia>>
        get() = dao.getAllMedias().map { notesAndMedia -> mapper.toRepo(notesAndMedia) }

    override fun addNoteAndMedia(noteAndMedia: NoteAndMedia) {
        dao.addNoteAndMedia(mapper.fromRepo(noteAndMedia))
    }

    override fun updateNoteAndMedia(noteAndMedia: NoteAndMedia) {
        dao.updateNoteAndMedia(mapper.fromRepo(noteAndMedia))
    }

    override fun deleteNoteAndMedia(noteAndMedia: NoteAndMedia) {
        dao.deleteNoteAndMedia(mapper.fromRepo(noteAndMedia))
    }
}