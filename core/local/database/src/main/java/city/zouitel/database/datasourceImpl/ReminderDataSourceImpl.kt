package city.zouitel.database.datasourceImpl

import city.zouitel.database.dao.ReminderDao
import city.zouitel.database.mapper.ReminderMapper
import city.zouitel.repository.datasource.ReminderDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import city.zouitel.repository.model.Reminder as OutReminder

/**
 * Implementation of the [ReminderDataSource] interface using a [ReminderDao] to access the database.
 * This class is responsible for providing data related to reminders.
 * It also implements the [ReminderMapper] interface to handle data mapping between domain and data layer.
 */
class ReminderDataSourceImpl(
    private val dao: ReminderDao,
    private val mapper: ReminderMapper
): ReminderDataSource {

    /**
     * Observes a list of reminders for a specific user ID.
     *
     * @param uid The user ID to filter reminders by.
     * @return A flow emitting a list of [OutReminder] objects for the given user ID.
     *         The list will be updated whenever there are changes in the database.
     */
    override suspend fun observeByUid(uid: String): Flow<List<OutReminder>> {
        return dao.observeByUid(uid).map { mapper.toRepo(it) }
    }

    /**
     * Inserts a new reminder into the data source.
     *
     * @param reminder The reminder to be inserted.
     */
    override suspend fun insert(reminder: OutReminder) {
        dao.insert(mapper.fromRepo(reminder))
    }

    /**
     * Updates an entity in the database based on its ID.
     *
     * @param id The ID of the entity to updateById.
     */
    override suspend fun updateById(id: Int) {
        dao.updateById(id)
    }

    /**
     * Deletes a reminder with the given ID.
     *
     * @param id The ID of the reminder entity to deleteById.
     */
    override suspend fun deleteById(id: Int) {
        dao.deleteById(id)
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
}