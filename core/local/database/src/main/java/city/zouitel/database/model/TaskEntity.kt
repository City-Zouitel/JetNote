package city.zouitel.database.model

import androidx.room.*
import city.zouitel.database.utils.Constants.DONE
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.TASKS
import city.zouitel.database.utils.Constants.TITLE

@Entity(
    tableName = TASKS
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID) val id: Long = 0L,
    @ColumnInfo(name = TITLE) var item: String? = null,
    @ColumnInfo(name = DONE) var isDone: Boolean = false
)
