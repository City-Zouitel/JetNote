package city.zouitel.database.model

import androidx.annotation.Keep
import androidx.room.*
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.TASKS_TABLE

@Entity(
    tableName = TASKS_TABLE
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID) val id:Long = 0L,
    var item:String? = null,
    var isDone:Boolean = false
)
