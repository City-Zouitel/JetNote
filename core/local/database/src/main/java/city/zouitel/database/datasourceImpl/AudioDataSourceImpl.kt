package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.AudioDao
import city.zouitel.database.mapper.AudioMapper
import city.zouitel.repository.datasource.AudioDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Audio as OutAudio

/**
 * Implementation of the [AudioDataSource] interface.
 *
 * This class is responsible for interacting with the local data layer (database)
 * to perform CRUD (Create, Read, Update, Delete) operations on audio entities.
 * It leverages an [AudioDao] for direct database interactions and an [AudioMapper]
 * for converting between domain and data layer models.
 *
 * @property dao The [AudioDao] instance used to interact with the database.
 * @property mapper The [AudioMapper] instance used for mapping between
 *                  [OutAudio] (domain model) and database entities.
 */
class AudioDataSourceImpl(
    private val dao: AudioDao,
    private val mapper: AudioMapper
): AudioDataSource {
    /**
     * A [Flow] that emits a list of all [OutAudio] entities in the data source.
     *
     * This flow observes all changes to the underlying data and emits a new list
     * whenever any changes occur. The emitted list contains [OutAudio] objects, which
     * are mapped from the underlying database entities using the [mapper].
     * Each item in the emitted list can be `null` if the underlying entity doesn't exist.
     *
     * The flow is obtained from the underlying [dao] (Data Access Object) which provides
     * a flow of database entities. This flow is then transformed by the [mapper] to
     * convert each database entity to its corresponding [OutAudio] representation.
     *
     * @see OutAudio
     * @see mapper
     * @see dao
     */
    override val observeAll: Flow<List<OutAudio?>>
        get() = dao.observeAll.map { mapper.toRepo(it) }

    /**
     * Observes an [OutAudio] entity by its unique identifier (UID).
     *
     * This function retrieves a stream of [OutAudio] objects from the underlying data source (likely a database),
     * identified by the provided [uid]. It uses a [Flow] to emit updates whenever the corresponding
     * data in the source changes. The retrieved data (likely from a Data Access Object or DAO) is then
     * mapped to the repository's domain model using the provided [mapper].
     *
     * @param uid The unique identifier of the [OutAudio] to observe.
     * @return A [Flow] that emits [OutAudio] objects whenever the data associated with the [uid] changes.
     *         Emits `null` if no [OutAudio] with the given [uid] is found.
     */
    override fun observeByUid(uid: String): Flow<OutAudio?> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    /**
     * Inserts an [OutAudio] entity into the data source.
     *
     * This function maps the [OutAudio] domain object to a data layer entity using the [mapper]
     * and then delegates the insertion to the underlying data access object ([dao]).
     *
     * This is a suspending function, meaning it can be safely called from within a coroutine.
     *
     * @param audio The [OutAudio] object to be inserted.
     */
    override suspend fun insert(audio: OutAudio) {
        dao.insert(mapper.fromRepo(audio))
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