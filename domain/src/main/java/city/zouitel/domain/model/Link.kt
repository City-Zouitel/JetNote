package city.zouitel.domain.model

import androidx.annotation.Keep
import city.zouitel.domain.utils.Constants.DEFAULT_INT
import city.zouitel.domain.utils.Constants.DEFAULT_TXT

/**
 * Represents a link with associated metadata.
 *
 * This data class encapsulates information about a web link, including its identifier,
 * unique user ID (UID), URL, title, description, image, and icon. It's designed to
 * store and manage link details within an application.
 *
 * @property id A unique numerical identifier for the link. Defaults to [DEFAULT_INT].
 * @property uid A unique string identifier associated with the user who created or owns the link. Defaults to [DEFAULT_TXT].
 * @property url The actual URL address of the link. Defaults to [DEFAULT_TXT].
 * @property title The title or headline of the linked content. Defaults to [DEFAULT_TXT].
 * @property description A brief summary or description of the linked content. Defaults to [DEFAULT_TXT].
 * @property image A URL or path to an image associated with the link (e.g., a thumbnail). Defaults to [DEFAULT_TXT].
 * @property icon A URL or path to an icon representing the link or its source. Defaults to [DEFAULT_TXT].
 *
 * @see DEFAULT_INT
 * @see DEFAULT_TXT
 */
@Keep
data class Link(
     val id: Int = DEFAULT_INT,
     val uid: String = DEFAULT_TXT,
     val url: String = DEFAULT_TXT,
     val title: String = DEFAULT_TXT,
     val description: String = DEFAULT_TXT,
     val image: String = DEFAULT_TXT,
     val icon: String = DEFAULT_TXT
)