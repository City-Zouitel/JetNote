package city.zouitel.database.model.relational

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import city.zouitel.database.model.DataEntity
import city.zouitel.database.model.NoteAndTag
import city.zouitel.database.model.Tag
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID

data class NoteEntity(
    @Embedded val dataEntity: DataEntity,
    @Relation(
        parentColumn = UUID, entityColumn = ID, entity = Tag::class,
        associateBy = Junction(
            NoteAndTag::class,
            parentColumn = "uid",
            entityColumn = "id"
        )
    )
    val tagEntities :List<Tag>
)
