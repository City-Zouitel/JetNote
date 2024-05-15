package city.zouitel.database.model

import androidx.room.*
import city.zouitel.database.utils.Constants.AUDIOS
import city.zouitel.database.utils.Constants.DURATION
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.PATH
import city.zouitel.database.utils.Constants.SIZE
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.URI

@Entity(tableName = AUDIOS)
data class AudioEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID) var id: Long = 0L,
    @ColumnInfo(TITLE) var title: String = "",
    @ColumnInfo(PATH) var path: String = "",
    @ColumnInfo(URI) var uri: String = "",
    @ColumnInfo(SIZE) var size: Long = 0L,
    @ColumnInfo(DURATION) var duration: Long = 0L,
)