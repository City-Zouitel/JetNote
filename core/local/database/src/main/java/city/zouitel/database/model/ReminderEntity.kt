package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.REMINDERS
import city.zouitel.database.utils.Constants.UUID

@Entity(tableName = REMINDERS)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = ID, defaultValue = "0") var id: Long,
    @ColumnInfo(name = UUID, defaultValue = "") var uid: String,
    @ColumnInfo(name = "Times", defaultValue = "0") var atTime: Long,
    @ColumnInfo(name = "Passed", defaultValue = "false") var isPassed: Boolean,
) {
    companion object {
        const val TABLE_NAME = REMINDERS
    }
}