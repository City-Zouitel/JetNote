package city.zouitel.database.model

import androidx.annotation.Keep
import androidx.room.Entity

@Entity(
    tableName = "note_and_label",
    primaryKeys = ["noteUid", "labelId"]
)
data class NoteAndTagEntity(
    val noteUid: String,
    val labelId: Long
)