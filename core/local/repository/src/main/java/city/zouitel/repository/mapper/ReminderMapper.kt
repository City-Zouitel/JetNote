package city.zouitel.repository.mapper

import city.zouitel.repository.model.Reminder
import city.zouitel.domain.model.Reminder as OutReminder

/**
 * A mapper class responsible for converting between [Reminder] (repo module) and [OutReminder] (domain layer) objects.
 */
class ReminderMapper {

    /**
     * Maps a list of Reminder data objects to a list of domain models.
     *
     * @param reminders The list of Reminder data objects to map.
     * @return A list of domain models representing the given reminders.
     */
    fun toDomain(reminders: List<Reminder>) = reminders.map { toDomain(it) }

    /**
     * Maps a Reminder data object to an OutReminder domain object.
     *
     * @param reminder The Reminder data object to map.
     * @return An OutReminder domain object representing the same reminder.
     */
    fun toDomain(reminder: Reminder) = OutReminder(
        id = reminder.id,
        uid = reminder.uid,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )

    /**
     * Maps an OutReminder (domain model) to a Reminder (data model).
     *
     * @param reminder The domain model representation of a reminder.
     * @return The data model representation of the reminder.
     */
    fun fromDomain(reminder: OutReminder) = Reminder(
        id = reminder.id,
        uid = reminder.uid,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )
}