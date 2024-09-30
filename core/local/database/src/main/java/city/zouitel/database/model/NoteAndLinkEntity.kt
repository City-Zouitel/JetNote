package city.zouitel.database.model

import androidx.annotation.Keep
import androidx.room.Entity

@Entity(
    tableName = "note_and_link",
    primaryKeys = ["noteUid", "linkId"]
)
data class NoteAndLinkEntity(
    val noteUid: String,
    val linkId: Long
)