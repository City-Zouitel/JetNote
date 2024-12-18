package city.zouitel.domain.model

import androidx.annotation.Keep

/**
 * Data class representing a reminder.
 *
 * @property id The unique identifier for the reminder. Defaults to 0.
 * @property uid The unique identifier of the user who owns this reminder. Defaults to an empty string.
 * @property atTime The time at which the reminder is scheduled to occur (in milliseconds since the epoch). Defaults to 0L.
 * @property isPassed A boolean flag indicating whether the reminder has passed its scheduled time. Defaults to false.
 */
@Keep
data class Reminder(
    val id: Int = 0,
    val uid: String = "",
    val atTime: Long = 0L,
    val isPassed: Boolean = false
)