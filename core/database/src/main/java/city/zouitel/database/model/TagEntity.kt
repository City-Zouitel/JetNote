package city.zouitel.database.model

import androidx.room.*
import city.zouitel.database.utils.Constants.COLOR
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.LABEL
import city.zouitel.database.utils.Constants.LABELS

@Entity(
    tableName = LABELS
)
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID) val id:Long = 0L,
    @ColumnInfo(name = LABEL) val label:String? = null,
    @ColumnInfo(name = COLOR, defaultValue = "0x0000") val color: Int = 0x0000
)