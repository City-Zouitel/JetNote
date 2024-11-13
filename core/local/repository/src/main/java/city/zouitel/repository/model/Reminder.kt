package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class Reminder(
    val id: Long = 0L,
    val uid: String = "",
    val atTime: Long = 0L,
    val isPassed: Boolean = false
)