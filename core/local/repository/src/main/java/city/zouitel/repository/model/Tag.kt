package city.zouitel.repository.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.Constants.DEFAULT_INT
import city.zouitel.domain.utils.Constants.DEFAULT_LONG
import city.zouitel.domain.utils.Constants.DEFAULT_TXT

/**
 * Represents a tag with an ID, label, and color.
 *
 * Tags are used to categorize or label items within the application.
 *
 * @property id The unique identifier for the tag. Defaults to [DEFAULT_LONG] if not provided.
 * @property label The text label associated with the tag. Defaults to [DEFAULT_TXT] if not provided.
 * @property color The color associated with the tag, typically represented as an integer (e.g., ARGB). Defaults to [DEFAULT_INT] if not provided.
 */
@Keep
data class Tag(
     val id: Long = DEFAULT_LONG,
     val label: String = DEFAULT_TXT,
     val color: Int = DEFAULT_INT
)