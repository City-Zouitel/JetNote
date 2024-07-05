package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.model.NoteAndMedia
import city.zouitel.domain.repository.NoteAndMediaRepository
import city.zouitel.repository.datasource.NoteAndMediaDataSource
import city.zouitel.repository.mapper.NoteAndMediaMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoteAndMediaRepoImpl(
    private val dataSource: NoteAndMediaDataSource,
    private val mapper: NoteAndMediaMapper
) : NoteAndMediaRepository {
    override val getAllNotesAndMedia: Flow<List<NoteAndMedia>>
        get() = dataSource.getAllNotesAndMedia.map { notesAndMedia -> mapper.toDomain(notesAndMedia) }

    override fun addNoteAndMedia(noteAndMedia: NoteAndMedia) {
        dataSource.addNoteAndMedia(mapper.fromDomain(noteAndMedia))
    }

    override fun updateNoteAndMedia(noteAndMedia: NoteAndMedia) {
        dataSource.updateNoteAndMedia(mapper.fromDomain(noteAndMedia))
    }

    override fun deleteNoteAndMedia(noteAndMedia: NoteAndMedia) {
        dataSource.deleteNoteAndMedia(mapper.fromDomain(noteAndMedia))
    }
}