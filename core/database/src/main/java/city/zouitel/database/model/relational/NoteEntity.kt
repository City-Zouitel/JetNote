package city.zouitel.database.model.relational

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import city.zouitel.database.model.*
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
        parentColumn = UUID, entityColumn = ID, entity = TaskEntity::class,
        associateBy = Junction(
            NoteAndTaskEntity::class,
            parentColumn = "noteUid",
            entityColumn = "taskId"
        )
    )
    val taskEntities:List<TaskEntity>,
    @Relation(
        parentColumn = UUID, entityColumn = ID, entity = LinkEntity::class,
        associateBy = Junction(
            NoteAndLinkEntity::class,
            parentColumn = "noteUid",
            entityColumn = "linkId"
        )
    )
    val linkEntities: List<LinkEntity>,
    @Relation(
        parentColumn = UUID, entityColumn = ID, entity = AudioEntity::class,
        associateBy = Junction(
            NoteAndAudioEntity::class,
            parentColumn = "noteUid",
            entityColumn = "audioId"
        )
    )
    val audioEntities: List<AudioEntity>,
    @Relation(
        parentColumn = UUID, entityColumn = ID, entity = MediaEntity::class,
        associateBy = Junction(
            NoteAndMediaEntity::class,
            parentColumn = "noteUid",
            entityColumn = "mediaId"
        )
    )
    val mediaEntities: List<MediaEntity>
)
