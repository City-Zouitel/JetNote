package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.model.Task.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.DONE
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.UUID

/**
 * Represents a task entity in the database.
 *
 * This data class defines the schema for the "tasks_table" in the Room database.
 * Each instance of this class represents a single task.
 *
 * @property id The unique identifier of the task. It serves as the primary key.
 *              It's manually set and not auto-generated. Defaults to 0.
 * @property uid A unique identifier (UUID) for the task, primarily used for synchronization or external referencing.
 *              Defaults to an empty string.
 * @property item The title or description of the task. Can be null. Defaults to "null".
 * @property isDone Indicates whether the task is completed or not. Defaults to false.
 *
 * @constructor Creates a new task with the specified properties.
 */
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
