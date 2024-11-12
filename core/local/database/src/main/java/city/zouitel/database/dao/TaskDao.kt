package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.TaskEntity
import city.zouitel.database.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks_table")
    fun getAllTaskItems(): Flow<List<TaskEntity>>

    @Query("SELECT * FROM tasks_table WHERE ${Constants.ID} = :id")
    suspend fun getTaskItemById(id: Long): TaskEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTaskItem(item: TaskEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTaskItem(item: TaskEntity)

    @Delete
    suspend fun deleteTaskItem(item: TaskEntity)
}