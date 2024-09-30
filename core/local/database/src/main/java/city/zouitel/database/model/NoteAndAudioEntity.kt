package city.zouitel.database.model

import androidx.room.Entity

@Entity(
    tableName = "note_and_audio",
    primaryKeys = ["noteUid", "audioId"]
)
data class NoteAndAudioEntity(
    val noteUid: String,
    val audioId: Long
)