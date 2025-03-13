package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.AudioRepo
import city.zouitel.repository.datasource.AudioDataSource
import city.zouitel.repository.mapper.AudioMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Audio as OutAudio

/**
 * Implementation of the [AudioRepo] interface.
 *
 * This class is responsible for managing audio data by interacting with a data source ([AudioDataSource])
 * and mapping between data transfer objects (DTOs) and domain models using an [AudioMapper].
 *
 * @property dataSource The data source responsible for persisting and retrieving audio data.
 * @property mapper The mapper responsible for converting between data layer and domain layer representations of audio.
 */
class AudioRepoImpl(
    private val dataSource: AudioDataSource,
    private val mapper: AudioMapper
): AudioRepo {
    /**
     * A [Flow] that emits a list of all [OutAudio] objects stored in the data source.
     *
     * This flow is derived from the underlying data source's `observeAll` flow and transforms
     * each emitted list of data source objects into a list of [OutAudio] domain objects
     * using the provided [mapper].
     *
     * Emits an empty list if no [OutAudio] objects are found in the data source.
     * Emits a new list whenever the underlying data source emits a change to its list.
     * Each element in the emitted list might be null if corresponding data in the data source is null.
     *
     * @see dataSource.observeAll The underlying flow from the data source.
     * @see mapper The mapper used to transform data source objects to domain objects.
     */
    override val observeAll: Flow<List<OutAudio?>>
        get() = dataSource.observeAll.map { mapper.toDomain(it) }

    /**
     * Observes an [OutAudio] object by its unique identifier (UID).
     *
     * This function retrieves a flow of [OutAudio] objects corresponding to the provided UID.
     * It leverages the underlying data source ([dataSource]) to fetch the data and then
     * transforms it into the domain model using the [mapper].
     *
     * The flow emits a single [OutAudio] object if found, or `null` if no object with the
     * given UID exists in the data source. Subsequent changes to the data source will be
     * reflected in new emissions from the flow.
     *
     * @param uid The unique identifier of the [OutAudio] object to observe.
     * @return A [Flow] that emits the [OutAudio] object associated with the provided UID,
     *         or `null` if no such object exists.
     */
    override fun observeByUid(uid: String): Flow<OutAudio?> {
        return dataSource.observeByUid(uid).map { mapper.toDomain(it) }
    }

    /**
     * Inserts an [OutAudio] domain object into the underlying data source.
     *
     * This function uses the provided [mapper] to convert the [OutAudio] domain object
     * to a data layer representation before inserting it using the [dataSource].
     *
     * This is a suspend function and should be called within a coroutine.
     *
     * @param audio The [OutAudio] domain object to be inserted.
     */
    override suspend fun insert(audio: OutAudio) {
        dataSource.insert(mapper.fromDomain(audio))
    }

    /**
     * Deletes a record from the data source using the provided unique identifier (UID).
     *
     * This function delegates the deletion operation to the underlying [dataSource].
     *
     * @param uid The unique identifier of the record to be deleted.
     */
    override suspend fun deleteByUid(uid: String) {
        dataSource.deleteByUid(uid)
    }

    /**
     * Deletes an entity from the data source by its unique identifier.
     *
     * This function delegates the deletion operation to the underlying [dataSource].
     *
     * @param id The unique identifier of the entity to be deleted.
     * @throws Exception if any error occurs during the deletion process within the data source. The specific exception depends on the implementation of `dataSource.delete()`.
     */
    override suspend fun deleteById(id: Long) {
        dataSource.deleteById(id)
    }
}