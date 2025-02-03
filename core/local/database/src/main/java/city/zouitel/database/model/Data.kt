package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.model.Data.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ARCHIVED
import city.zouitel.database.utils.Constants.COLOR
import city.zouitel.database.utils.Constants.DATE
import city.zouitel.database.utils.Constants.DEFAULT_BOOLEAN
import city.zouitel.database.utils.Constants.DEFAULT_NUM
import city.zouitel.database.utils.Constants.DEFAULT_TXT
import city.zouitel.database.utils.Constants.DESCRIPTION
import city.zouitel.database.utils.Constants.PRIORITY
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.TXT_COLOR
import city.zouitel.database.utils.Constants.UUID

/**
 * Represents a data entry in the `note_data_table` database table.
 *
 * This data class defines the structure of a note, including its unique identifier,
 * title, description, priority, color scheme, date, and archived status.
 *
 * @property uid The unique identifier for this data entry. Serves as the primary key.
 *               It is a String and is not auto-generated. Must be provided when creating a new entry.
 * @property title The title of the note.
 * @property description The detailed description or content of the note.
 * @property priority The priority level of the note, represented as an integer.
 * @property background The background color of the note, represented as an integer.
 * @property textColor The text color of the note, represented as an integer.
 * @property date The date associated with the note, stored as a String.
 * @property archived Indicates whether the note has been archived (true) or not (false).
 */
@Entity(tableName = TABLE_NAME)
data class Data(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = UUID, defaultValue = DEFAULT_TXT) val uid: String,
    @ColumnInfo(name = TITLE, defaultValue = DEFAULT_TXT) val title: String,
    @ColumnInfo(name = DESCRIPTION, defaultValue = DEFAULT_TXT) val description: String,
    @ColumnInfo(name = PRIORITY, defaultValue = DEFAULT_NUM) val priority: Int,
    @ColumnInfo(name = COLOR, defaultValue = DEFAULT_NUM) val background: Int,
    @ColumnInfo(name = TXT_COLOR, defaultValue = DEFAULT_NUM) val textColor: Int,
    @ColumnInfo(name = DATE, defaultValue = DEFAULT_TXT) val date: String,
    @ColumnInfo(name = ARCHIVED, defaultValue = DEFAULT_BOOLEAN) val archived: Boolean
) {
    companion object {
        const val TABLE_NAME = "note_data_table"
    }
}
