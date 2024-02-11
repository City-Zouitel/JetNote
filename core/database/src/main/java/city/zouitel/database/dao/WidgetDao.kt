package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import city.zouitel.database.model.relational.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WidgetDao {
    @Transaction
    @Query("select * from NOTES_TABLE where REMOVED = 0 order by Date desc")
    fun allWidgetEntitiesById(): Flow<List<NoteEntity>>
}