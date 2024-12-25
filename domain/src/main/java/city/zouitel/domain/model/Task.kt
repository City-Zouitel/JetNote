package city.zouitel.domain.model

import androidx.annotation.Keep

/**
 * Represents a single task in a to-do list.
 *
 * @property id The unique identifier for the task. Defaults to 0L for new, unsaved tasks.
 * @property uid A unique identifier for the task that can be used across devices or databases, if needed. Defaults to an empty string.
 * @property item The description or content of the task. Can be null if there's no specific description.
 * @property isDone Indicates whether the task has been completed. Defaults to false.
 */
@Keep
data class Task(
    val id: Long = 0L,
    val uid: String = "",
    val item: String? = null,
    val isDone: Boolean = false
)
