package city.zouitel.database.model

import androidx.room.Entity

@Entity(
    tableName = "note_and_task",
    primaryKeys = ["noteUid","taskId"]
)
data class NoteAndTaskEntity(
    val noteUid:String,
    val taskId:Long
)
