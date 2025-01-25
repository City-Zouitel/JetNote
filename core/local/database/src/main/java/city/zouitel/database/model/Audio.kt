package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.model.Audio.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.DEFAULT_NUM
import city.zouitel.database.utils.Constants.DEFAULT_TXT
import city.zouitel.database.utils.Constants.DURATION
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.PATH
import city.zouitel.database.utils.Constants.SIZE
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.URI
import city.zouitel.database.utils.Constants.UUID

/**
 * Represents an audio file stored in the database.
 *
 * This data class is an entity in the Room database, representing a single audio file
 * with its metadata. It defines the structure of the "audios_table".
 *
 * @property id The unique identifier for the audio file within the database.
 *              It is auto-generated and serves as the primary key.
 * @property uid A universally unique identifier (UUID) assigned to the audio file.
 *              This is useful for identifying the audio file across different systems or instances.
 * @property title The display title of the audio file.
 * @property path The file path where the audio file is stored on the device.
 * @property uri The content URI of the audio file, which can be used to access it through the content provider.
 * @property size The file size of the audio file in bytes.
 * @property duration The duration of the audio file in milliseconds.
 *
 * @see androidx.room.Entity
 * @see androidx.room.PrimaryKey
 * @see androidx.room.ColumnInfo
 */
@Entity(tableName = TABLE_NAME)
data class Audio(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID, defaultValue = DEFAULT_NUM) val id: Long,
    @ColumnInfo(UUID, defaultValue = DEFAULT_TXT) val uid: String,
    @ColumnInfo(TITLE, defaultValue = DEFAULT_TXT) val title: String,
    @ColumnInfo(PATH, defaultValue = DEFAULT_TXT) val path: String,
    @ColumnInfo(URI, defaultValue = DEFAULT_TXT) val uri: String,
    @ColumnInfo(SIZE, defaultValue = DEFAULT_NUM) val size: Long,
    @ColumnInfo(DURATION, defaultValue = DEFAULT_NUM) val duration: Long,
) {
    companion object {
        const val TABLE_NAME = "audios_table"
    }
}