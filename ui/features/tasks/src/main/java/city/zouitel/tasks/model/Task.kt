package city.zouitel.tasks.model

import androidx.annotation.Keep

@Keep
data class Task(
    val id: Long = 0L,
    val uid: String = "",
    var item: String? = null,
    var isDone: Boolean = false
)
