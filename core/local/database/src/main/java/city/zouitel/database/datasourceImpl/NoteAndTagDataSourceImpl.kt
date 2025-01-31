package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.NoteAndTagDao
import city.zouitel.database.mapper.NoteAndTagMapper
import city.zouitel.repository.datasource.NoteAndTagDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.NoteAndTag as OutNoteAndTag

/**
 * Implementation of the [NoteAndTagDataSource] interface.
 *
 * This class handles the interaction with the data layer for Note and Tag entities,
 * using a [NoteAndTagDao] for database operations and a [NoteAndTagMapper] for
 * data conversion between the data and repository layers.
 *
 * @property dao The Data Access Object for Note and Tag entities.
 * @property mapper The mapper responsible for converting between data and repository layer objects.
 */
class NoteAndTagDataSourceImpl(
    private val dao: NoteAndTagDao,
    private val mapper: NoteAndTagMapper
): NoteAndTagDataSource {
    /**
     * A flow that emits a list of [OutNoteAndTag] whenever there's a change in the underlying data source.
     *
     * This observable flow retrieves all notes and their associated tags from the data access object (DAO)
     * and maps them to the repository's representation ([OutNoteAndTag]) using the provided [mapper].
     *
     * @return A [Flow] emitting a [List] of [OutNoteAndTag] objects.
     */
    override val observeAll: Flow<List<OutNoteAndTag>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    /**
     * Inserts a [OutNoteAndTag] into the database.
     *
     * This function takes an [OutNoteAndTag] representing a note and its associated tag,
     * maps it to the internal database representation ([NoteAndTagEntity]), and then
     * inserts it into the database using the DAO.
     *
     * @param noteAndTag The [OutNoteAndTag] to be inserted.
     */
    override suspend fun insert(noteAndTag: OutNoteAndTag) {
        dao.insert(mapper.fromRepo(noteAndTag))
    }

    /**
     * Deletes a record from the data source with the specified unique ID (UID).
     *
     * This function suspends the coroutine execution while the deletion operation is performed.
     * It delegates the actual deletion to the underlying [dao] (Data Access Object).
     *
     * @param uid The unique identifier (UID) of the record to be deleted.
     * @throws Exception if there is an error during the deletion process (specific exceptions depend on DAO implementation).
     */
    override suspend fun deleteByUid(uid: String) {
        dao.deleteByUid(uid)
    }

    /**
     * Deletes an entity from the data source by its ID.
     *
     * @param id The ID of the entity to delete.
     * @throws Exception if an error occurs during the deletion process.
     */
    override suspend fun deleteById(id: Long) {
        dao.deleteById(id)
    }
}