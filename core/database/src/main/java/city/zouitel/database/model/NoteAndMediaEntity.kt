package city.zouitel.database.model

import androidx.room.Entity


@Entity(
    tableName = "note_and_media",
    primaryKeys = ["noteUid", "mediaId"]
)
data class NoteAndMediaEntity(
    var noteUid: String,
    var mediaId: Long
)