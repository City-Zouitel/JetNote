package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.REMINDERS

@Entity(REMINDERS)
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID) var id: Long = 0L,
    @ColumnInfo("Times") var atTime: Long = 0L,
    @ColumnInfo("Passed") var isPassed: Boolean = false,
) {
    companion object {
        const val TABLE_NAME = REMINDERS
    }
}