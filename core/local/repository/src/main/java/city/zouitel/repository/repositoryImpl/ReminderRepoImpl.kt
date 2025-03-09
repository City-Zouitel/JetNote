package city.zouitel.repository.repositoryImpl

import city.zouitel.domain.repository.ReminderRepo
import city.zouitel.repository.datasource.ReminderDataSource
import city.zouitel.repository.mapper.ReminderMapper
import city.zouitel.repository.model.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.domain.model.Reminder as OutReminder

/**
 * Implementation of the [ReminderRepo] interface.
 * This class interacts with the [dataSource] to perform CRUD operations on reminders.
 * It uses the [mapper] to convert between domain model [OutReminder] and repo model [Reminder].
 */
class ReminderRepoImpl(
    private val dataSource: ReminderDataSource,
    private val mapper: ReminderMapper
): ReminderRepo {

    /**
     * Observes reminders for a specific user ID.
     *
     * This function observes the reminders associated with the given user ID and emits a flow of
     * domain-specific reminder objects (OutReminder).
     * It leverages the data source to retrieve the reminders and then uses a mapper to transform
     * them into the desired domain format.
     *
     * @param uid The user ID for which to observe reminders.
     * @return A flow of lists of OutReminder objects, representing the reminders for the user.
     */
    override suspend fun observeByUid(uid: String): Flow<List<OutReminder>> {
        return dataSource.observeByUid(uid).map { reminders -> mapper.toDomain(reminders) }
    }

    /**
     * Inserts a new reminder into the data source.
     *
     * @param reminder The reminder to be inserted.
     */
    override suspend fun insert(reminder: OutReminder) {
        dataSource.insert(mapper.fromDomain(reminder))
    }

    /**
     * Updates an entity with the given ID.
     *
     * @param id The ID of the entity to update.
     */
    override suspend fun updateById(id: Int) {
        dataSource.updateById(id)
    }

    /**
     * Deletes an entity with the given ID.
     *
     * @param id The ID of the entity to delete.
     */
    override suspend fun deleteById(id: Int) {
        dataSource.deleteById(id)
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

    override suspend fun deleteDrafts() {
        dataSource.deleteDrafts()
    }
}