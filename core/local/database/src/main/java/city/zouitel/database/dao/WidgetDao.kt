package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import city.zouitel.database.model.relational.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WidgetDao {
    @Transaction
    @Query("SELECT * FROM notes_table WHERE removed = 0 ORDER BY date DESC")
    fun allWidgetEntitiesById(): Flow<List<NoteEntity>>
}