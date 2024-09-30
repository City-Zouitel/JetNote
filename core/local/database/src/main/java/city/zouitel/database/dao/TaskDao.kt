package city.zouitel.database.dao

import androidx.room.*
import city.zouitel.database.model.TaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks_table")
    fun getAllTaskItems():Flow<List<TaskEntity>>

    @Insert
    suspend fun addTaskItem(item: TaskEntity)

    @Update
    suspend fun updateTaskItem(item: TaskEntity)

    @Delete
    suspend fun deleteTaskItem(item: TaskEntity)
}