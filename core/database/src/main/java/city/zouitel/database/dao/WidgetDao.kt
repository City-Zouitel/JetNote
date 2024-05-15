package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import city.zouitel.database.model.relational.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WidgetDao {
    @Transaction
    @Query("SELECT * FROM NOTES_TABLE WHERE REMOVED = 0 ORDER BY DATE DESC")
    fun allWidgetEntitiesById(): Flow<List<NoteEntity>>
}