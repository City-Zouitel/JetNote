package city.zouitel.database.model

import androidx.room.Entity

@Entity(
    tableName = "note_and_reminder",
    primaryKeys = ["noteId", "reminderId"]
)
data class NoteAndReminderEntity(
    val noteId: String,
    val reminderId: Long
)
