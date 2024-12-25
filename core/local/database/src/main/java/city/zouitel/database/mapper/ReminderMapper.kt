package city.zouitel.database.mapper

import city.zouitel.database.model.Reminder
import city.zouitel.repository.model.Reminder as OutReminder


/**
 * A mapper class responsible for converting between repository [Reminder] and database [OutReminder].
 * This class provides methods for mapping lists of reminders and individual reminders between the two types.
 */
class ReminderMapper {

    /**
     * Maps a list of Reminder objects to a list of their repository representations.
     *
     * @param reminders The list of Reminder objects to map.
     * @return A list containing the repository representations of the given reminders
     * that are mapped from Reminder to OutReminder.
     */
    fun toRepo(reminders: List<Reminder>) = reminders.map { toRepo(it) }

    /**
     * Maps a Reminder object to an OutReminder object.
     *
     * This function converts a Reminder object, which is typically used internally,
     * to an OutReminder object, which is likely used for external communication or data transfer.
     *
     * @param reminder The input Reminder object to be mapped.
     * @return An OutReminder object representing the mapped Reminder.
     */
    fun toRepo(reminder: Reminder) = OutReminder(
        id = reminder.id,
        uid = reminder.uid,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )

    /**
     * Maps an OutReminder data object from the repository layer to a Reminder data object for the repository layer.
     *
     * @param reminder The OutReminder object to map.
     * @return A Reminder object representing the same data.
     */
    fun fromRepo(reminder: OutReminder) = Reminder(
        id = reminder.id,
        uid = reminder.uid,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )
}