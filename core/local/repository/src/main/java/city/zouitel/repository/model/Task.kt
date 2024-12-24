package city.zouitel.repository.model

import androidx.annotation.Keep

@Keep
data class Task(
    val id: Long = 0L,
    val uid: String = "",
    val item: String? = null,
    val isDone: Boolean = false
)
