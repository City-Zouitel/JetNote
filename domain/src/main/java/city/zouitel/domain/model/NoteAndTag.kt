package city.zouitel.domain.model

import androidx.annotation.Keep

/**
 * Represents the association between a Note and a Tag.
 *
 * This data class is used to represent the relationship between a note and a tag,
 * primarily used within a database or data storage context. It links a note identified
 * by a unique string [uid] with a tag identified by a numerical [id].
 *
 * @property uid The unique identifier of the Note (e.g., UUID or other unique string).
 * @property id The unique identifier of the Tag, typically a numerical ID.
 */
@Keep
data class NoteAndTag(
    val uid: String,
    val id: Long
)
