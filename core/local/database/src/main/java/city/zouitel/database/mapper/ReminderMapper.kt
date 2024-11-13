package city.zouitel.database.mapper

import city.zouitel.database.model.ReminderEntity as InReminder
import city.zouitel.repository.model.Reminder as OutReminder


class ReminderMapper {

    fun toRepo(reminders: List<InReminder>) = reminders.map { toRepo(it) }

    fun toRepo(reminder: InReminder) = OutReminder(
        id = reminder.id,
        uid = reminder.uid,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )

    fun fromRepo(reminder: OutReminder) = InReminder(
        id = reminder.id,
        uid = reminder.uid,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )
}