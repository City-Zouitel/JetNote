package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.database.model.NoteAndTaskEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndTaskDao {

    @Query("SELECT * FROM NOTE_AND_TASK")
    fun getAllNoteAndTasks(): Flow<List<NoteAndTaskEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteAndTask(noteAndTodo: NoteAndTaskEntity)

    @Delete
    suspend fun deleteNoteAndTask(noteAndTodo: NoteAndTaskEntity)
}