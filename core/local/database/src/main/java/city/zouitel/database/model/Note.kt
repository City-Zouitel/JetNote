package city.zouitel.database.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID

/**
 * Represents a Note entity, containing core data and a list of associated tags.
 *
 * This class defines the structure of a note, leveraging Room's object-relational mapping (ORM) features
 * to manage its data and relationships with other entities.
 *
 * @property data The core data associated with this note.  It is embedded directly within the Note table.
 *                 See [Data] for details on the embedded data structure.
 * @property tags A list of [Tag] objects associated with this note. This relationship is many-to-many.
 *                 The relation is handled by Room using the [NoteAndTag] junction table.
 *
 * The `tags` property is populated automatically by Room when querying for `Note` objects.
 * Room performs a join operation behind the scenes to retrieve the associated tags based on
 * the `NoteAndTag` junction table.
 *
 * @see Data
 * @see Tag
 * @see NoteAndTag
 */
data class Note(
    @Embedded val data: Data,
    @Relation(
        parentColumn = UUID, entityColumn = ID, entity = Tag::class,
        associateBy = Junction(
            NoteAndTag::class,
            parentColumn = "uid",
            entityColumn = "id"
        )
    )
    val tags: List<Tag>
)
