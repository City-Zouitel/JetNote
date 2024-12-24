package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.model.Task.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.DONE
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.UUID

@Entity(tableName = TABLE_NAME)
data class Task(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID, defaultValue = "0") val id: Long,
    @ColumnInfo(name = UUID, defaultValue = "") val uid: String,
    @ColumnInfo(name = TITLE, defaultValue = "null") val item: String?,
    @ColumnInfo(name = DONE, defaultValue = "false") val isDone: Boolean = false
) {
    companion object {
        const val TABLE_NAME = "tasks_table"
    }
}
