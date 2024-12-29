package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.model.Media.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.DEFAULT_BOOLEAN
import city.zouitel.database.utils.Constants.DEFAULT_NUM
import city.zouitel.database.utils.Constants.DEFAULT_TXT
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.URI
import city.zouitel.database.utils.Constants.UUID
import city.zouitel.database.utils.Constants.VIDEO

@Entity(TABLE_NAME)
data class Media(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID, defaultValue = DEFAULT_NUM) val id: Long,
    @ColumnInfo(UUID, defaultValue = DEFAULT_TXT) val uid: String,
    @ColumnInfo(VIDEO, defaultValue = DEFAULT_BOOLEAN) val isVideo: Boolean,
    @ColumnInfo(URI, defaultValue = DEFAULT_TXT) val path: String
) {
    companion object {
        const val TABLE_NAME = "media_table"
    }
}