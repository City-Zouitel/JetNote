package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.database.model.Task
import city.zouitel.database.model.Task.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.DONE
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {

    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Task>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: Task)

    @Query("UPDATE $TABLE_NAME SET $DONE = NOT $DONE WHERE $ID = :id")
    suspend fun updateById(id: Long)

    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    suspend fun deleteById(id: Long)
}