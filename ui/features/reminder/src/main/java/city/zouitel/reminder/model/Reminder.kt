package city.zouitel.reminder.model

import androidx.annotation.Keep

@Keep
data class Reminder(
    val id: Int = 0,
    val uid: String = "",
    val atTime: Long = 0L,
    val isPassed: Boolean = false
)