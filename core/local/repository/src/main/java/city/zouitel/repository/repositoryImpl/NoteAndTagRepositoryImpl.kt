package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.NoteAndTagRepository
import city.zouitel.repository.datasource.NoteAndTagDataSource
import city.zouitel.repository.mapper.NoteAndTagMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.NoteAndTag as OutNoteAndTag

/**
 * Implementation of the [NoteAndTagRepository] interface.
 *
 * This class handles the data access logic for note and tag associations,
 * interacting with a [NoteAndTagDataSource] for data persistence and a
 * [NoteAndTagMapper] for data transformations between the data layer and the domain layer.
 *
 * @property dataSource The data source for note and tag associations.
 * @property mapper The mapper for converting between data and domain layer representations of note and tag associations.
 */
class NoteAndTagRepositoryImpl(
    private val dataSource: NoteAndTagDataSource,
    private val mapper: NoteAndTagMapper
): NoteAndTagRepository {
    /**
     * A [Flow] that emits a list of [OutNoteAndTag] whenever there is a change in the underlying data source.
     *
     * This property provides a reactive stream of all notes and their associated tags.  It observes changes
     * in the data source, and when a change occurs, it transforms the raw data from the data source
     * ([dataSource.observeAll]) into domain objects ([OutNoteAndTag]) using the [mapper].
     *
     * @see dataSource.observeAll for the underlying data source flow.
     * @see mapper.toDomain for the transformation logic.
     *
     * @return A [Flow] emitting a [List] of [OutNoteAndTag] objects.
     */
    override val observeAll: Flow<List<OutNoteAndTag>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    /**
     * Inserts a [OutNoteAndTag] into the data source.
     *
     * This function takes a [OutNoteAndTag] object, maps it to its data source representation using the
     * configured [mapper], and then inserts it into the underlying data source. This operation is
     * performed asynchronously as a suspending function.
     *
     * @param noteAndTag The [OutNoteAndTag] object to be inserted.
     */
    override suspend fun insert(noteAndTag: OutNoteAndTag) {
        dataSource.insert(mapper.fromDomain(noteAndTag))
    }

    /**
     * Deletes an entity from the data source by its unique identifier.
     *
     * This function delegates the deletion operation to the underlying [dataSource].
     *
     * @param id The unique identifier of the entity to be deleted.
     * @throws Exception if any error occurs during the deletion process within the data source. The specific exception depends on the implementation of `dataSource.delete()`.
     */
    override suspend fun delete(id: Long) {
        dataSource.delete(id)
    }

    /**
     * Deletes all draft messages from the data source.
     *
     * This function clears the storage of any messages that were saved as drafts,
     * meaning they were created but not yet sent.  After calling this function,
     * no draft messages should remain in the underlying data source.
     */
    override suspend fun deleteDrafts() {
        dataSource.deleteDrafts()
    }
}