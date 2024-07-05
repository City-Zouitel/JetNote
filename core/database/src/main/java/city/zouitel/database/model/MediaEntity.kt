package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.MEDIA
import city.zouitel.database.utils.Constants.PATH

@Entity(MEDIA)
data class MediaEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID) var id: Long = 0,
    @ColumnInfo("is video") var isVideo: Boolean = false,
    @ColumnInfo(PATH) var path: String = ""
)