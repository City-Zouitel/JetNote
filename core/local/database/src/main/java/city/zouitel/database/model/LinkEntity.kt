package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import city.zouitel.database.utils.Constants.DESCRIPTION
import city.zouitel.database.utils.Constants.HOST
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.IMG_LINK
import city.zouitel.database.utils.Constants.LINKS
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.URL

@Entity(
    tableName = LINKS
)
data class LinkEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(ID) var id: Long = 0L,
    @ColumnInfo(URL) var url: String = "",
    @ColumnInfo(HOST) var host: String = "",
    @ColumnInfo(IMG_LINK) var image: String? = "",
    @ColumnInfo(TITLE) val title: String? = "",
    @ColumnInfo(DESCRIPTION) var description: String? = ""
)