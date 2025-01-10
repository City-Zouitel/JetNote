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

/**
 * Represents a link entity stored in the database.
 *
 * This data class defines the structure of a link, including its unique identifier,
 * URL, title, description, image link, and icon link. It is mapped to the
 * [TABLE_NAME] in the database.
 *
 * @property id The unique integer identifier of the link. It is the primary key and is not auto-generated.
 * @property uid A universally unique identifier (UUID) for the link.
 * @property url The URL of the link.
 * @property title The title associated with the link.
 * @property description A brief description of the link.
 * @property image The URL of an image associated with the link.
 * @property icon The URL of an icon associated with the link.
 *
 * @see TABLE_NAME
 */
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