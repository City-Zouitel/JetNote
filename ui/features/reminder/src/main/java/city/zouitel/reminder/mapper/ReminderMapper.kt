package city.zouitel.reminder.mapper

import city.zouitel.domain.model.Reminder as OutReminder
import city.zouitel.reminder.model.Reminder as InReminder

class ReminderMapper {
    fun fromDomain(reminders: List<OutReminder>) = reminders.map { fromDomain(it) }

    fun toDomain(reminder: InReminder) = OutReminder(
        id = reminder.id,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )

    fun fromDomain(reminder: OutReminder) = InReminder(
        id = reminder.id,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )
}