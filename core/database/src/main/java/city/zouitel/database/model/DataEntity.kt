package city.zouitel.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.ColumnInfo
import city.zouitel.database.utils.Constants.AUDIO_DURATION
import city.zouitel.database.utils.Constants.AUDIO_URL
import city.zouitel.database.utils.Constants.COLOR
import city.zouitel.database.utils.Constants.DATE
import city.zouitel.database.utils.Constants.DESCRIPTION
import city.zouitel.database.utils.Constants.IMAGE_UIL
import city.zouitel.database.utils.Constants.NON
import city.zouitel.database.utils.Constants.NOTES_TABLE
import city.zouitel.database.utils.Constants.PRIORITY
import city.zouitel.database.utils.Constants.REMINDING
import city.zouitel.database.utils.Constants.TEXT_COLOR
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.REMOVED
import city.zouitel.database.utils.Constants.UUID

@Entity(
    tableName = NOTES_TABLE
)
data class DataEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = UUID) var uid: String = "",
    @ColumnInfo(name = TITLE) var title: String? = null,
    @ColumnInfo(name = DESCRIPTION) var description: String? = null,
    @ColumnInfo(name = PRIORITY) var priority: String = NON,
    @ColumnInfo(name = COLOR) var color: Int = 0,
    @ColumnInfo(name = TEXT_COLOR) var textColor: Int = 0x000000,
    @ColumnInfo(name = DATE) var date: String = "",
    @ColumnInfo(name = REMOVED) var removed: Int = 0,
    @ColumnInfo(name = AUDIO_DURATION) var audioDuration: Int = 0,
    @ColumnInfo(name = REMINDING) var reminding: Long = 0L,
    @ColumnInfo(name = IMAGE_UIL) var imageUrl: String? = null,
    @ColumnInfo(name = AUDIO_URL) var audioUrl: String? = null,
)
