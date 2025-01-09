package city.zouitel.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import city.zouitel.database.model.Link.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.DEFAULT_NUM
import city.zouitel.database.utils.Constants.DEFAULT_TXT
import city.zouitel.database.utils.Constants.DESCRIPTION
import city.zouitel.database.utils.Constants.ICON
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.IMG_LINK
import city.zouitel.database.utils.Constants.TITLE
import city.zouitel.database.utils.Constants.URL
import city.zouitel.database.utils.Constants.UUID

@Entity(tableName = TABLE_NAME)
data class Link(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(ID, defaultValue = DEFAULT_NUM) val id: Int,
    @ColumnInfo(UUID, defaultValue = DEFAULT_TXT) val uid: String,
    @ColumnInfo(URL, defaultValue = DEFAULT_TXT) val url: String,
    @ColumnInfo(TITLE, defaultValue = DEFAULT_TXT) val title: String,
    @ColumnInfo(DESCRIPTION, defaultValue = DEFAULT_TXT) val description: String,
    @ColumnInfo(IMG_LINK, defaultValue = DEFAULT_TXT) val image: String,
    @ColumnInfo(ICON, defaultValue = DEFAULT_TXT) val icon: String
) {
    companion object {
        const val TABLE_NAME = "links_table"
    }
}