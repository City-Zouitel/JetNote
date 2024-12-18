package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.model.Reminder.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.PASSED
import city.zouitel.database.utils.Constants.TIME
import city.zouitel.database.utils.Constants.UUID

/**
 * Data class representing a Reminder entity.
 *
 * This class is used to store information about a reminder, including its ID, UUID, time, and whether it has passed.
 * It is annotated with @Entity to indicate that it is a database entity and will be stored in a table named "reminders_table".
 *
 * @property id The unique identifier of the reminder.
 * @property uid The universally unique identifier of the reminder.
 * @property atTime The time at which the reminder is scheduled.
 * @property isPassed A boolean indicating whether the reminder has passed.
 */
@Entity(tableName = TABLE_NAME)
data class Reminder(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID, defaultValue = "0") val id: Int,
    @ColumnInfo(name = UUID, defaultValue = "") val uid: String,
    @ColumnInfo(name = TIME, defaultValue = "0") val atTime: Long,
    @ColumnInfo(name = PASSED, defaultValue = "false") val isPassed: Boolean
) {
    companion object {
        const val TABLE_NAME = "reminders_table"
    }
}