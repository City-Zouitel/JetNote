package city.zouitel.database.model

import androidx.room.Entity
import city.zouitel.database.model.NoteAndTag.Companion.TABLE_NAME

/**
 * Represents the many-to-many relationship between notes and tags (labels).
 *
 * This entity is used by Room to represent the join table between the "Note" and "Tag" entities.
 * It doesn't store any additional data beyond the foreign keys.
 *
 * @property uid The unique identifier of the Note associated with this relationship.
 * @property id The unique identifier of the Tag associated with this relationship.
 */
@Entity(tableName = TABLE_NAME, primaryKeys = ["uid", "id"])
data class NoteAndTag(val uid: String, val id: Long) {
    companion object {
        const val TABLE_NAME = "note_and_label"
    }
}