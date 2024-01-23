package city.zouitel.database.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import city.zouitel.database.utils.Constants.DESCRIPTION
import city.zouitel.database.utils.Constants.HOST
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.IMG_LINK
import city.zouitel.database.utils.Constants.LINKS_TABLE
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.URL

@Entity(
    tableName = LINKS_TABLE
)
data class LinkEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(ID) var id: String = "",
    @ColumnInfo(URL) var url: String = "",
    @ColumnInfo(HOST) var host: String = "",
    @ColumnInfo(IMG_LINK) var image: String? = "",
    @ColumnInfo(TITLE) val title: String? = "",
    @ColumnInfo(DESCRIPTION) var description: String? = ""
)