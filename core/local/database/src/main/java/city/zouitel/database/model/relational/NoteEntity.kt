package city.zouitel.database.model.relational

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import city.zouitel.database.model.AudioEntity
import city.zouitel.database.model.DataEntity
import city.zouitel.database.model.NoteAndAudioEntity
import city.zouitel.database.model.NoteAndTagEntity
import city.zouitel.database.model.TagEntity
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID

data class NoteEntity(
    @Embedded val dataEntity: DataEntity,
    @Relation(
        parentColumn = UUID, entityColumn = ID, entity = TagEntity::class,
        associateBy = Junction(
            NoteAndTagEntity::class,
            parentColumn = "noteUid",
            entityColumn = "labelId"
        )
    )
    val tagEntities :List<TagEntity>,
    @Relation(
        parentColumn = UUID, entityColumn = ID, entity = AudioEntity::class,
        associateBy = Junction(
            NoteAndAudioEntity::class,
            parentColumn = "noteUid",
            entityColumn = "audioId"
        )
    )
    val audioEntities: List<AudioEntity>
)
