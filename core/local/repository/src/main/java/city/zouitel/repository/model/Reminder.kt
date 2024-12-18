package city.zouitel.repository.model

import androidx.annotation.Keep

/**
 * Data class representing a reminder.
 *
 * @property id The unique identifier of the reminder. Defaults to 0.
 * @property uid The unique identifier of the user this reminder belongs to. Defaults to an empty string.
 * @property atTime The time when the reminder is scheduled, in milliseconds since the epoch. Defaults to 0L.
 * @property isPassed A flag indicating whether the reminder has already passed its scheduled time. Defaults to false.
 */
@Keep
data class Reminder(
    val id: Int = 0,
    val uid: String = "",
    val atTime: Long = 0L,
    val isPassed: Boolean = false
)