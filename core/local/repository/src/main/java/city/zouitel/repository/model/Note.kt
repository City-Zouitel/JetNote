package city.zouitel.repository.model

import androidx.annotation.Keep

/**
 * Represents a note containing data and associated tags.
 *
 * A note is a structured piece of information that can be categorized
 * and organized using tags.  It consists of the actual content ([data])
 * and a list of associated [tags] that provide context or classification.
 *
 * @property data The primary content or data of the note.
 * @property tags A list of [Tag]s associated with this note, used for categorization and organization.
 */
@Keep
data class Note(val data: Data, val tags: List<Tag>)
