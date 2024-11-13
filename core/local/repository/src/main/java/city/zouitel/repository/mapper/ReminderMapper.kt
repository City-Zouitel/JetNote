package city.zouitel.repository.mapper

import city.zouitel.domain.model.Reminder as OutReminder
import city.zouitel.repository.model.Reminder as InReminder

class ReminderMapper {
    fun toDomain(reminders: List<InReminder>) = reminders.map { toDomain(it) }

    fun toDomain(reminder: InReminder) = OutReminder(
        id = reminder.id,
        uid = reminder.uid,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )

    fun fromDomain(reminder: OutReminder) = InReminder(
        id = reminder.id,
        uid = reminder.uid,
        atTime = reminder.atTime,
        isPassed = reminder.isPassed
    )
}