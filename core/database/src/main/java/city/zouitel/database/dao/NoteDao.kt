package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import city.zouitel.database.model.relational.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Transaction
    @Query("select * from NOTES_TABLE where REMOVED = 0 order by Date asc")
    fun getAllNotesById():Flow<List<NoteEntity>>

    @Transaction
    @Query("select * from NOTES_TABLE where REMOVED = 0 order by Date desc")
    fun getAllNotesByNewest():Flow<List<NoteEntity>>

    @Transaction
    @Query("select * from NOTES_TABLE where REMOVED = 0 order by Date asc")
    fun getAllNotesByOldest():Flow<List<NoteEntity>>

    @Transaction
    @Query("select * from NOTES_TABLE where REMOVED = 0 order by TITLE asc")
    fun getAllNotesByName():Flow<List<NoteEntity>>

    @Transaction
@Query("select * from NOTES_TABLE where REMOVED = 0 order by case " +
            "when Priority like 'URGENT' then 1 " +
            "when Priority like 'IMPORTANT' then 2 " +
            "when Priority like 'NORMAL' then 3 " +
            "when Priority like 'LOW' then 4 " +
            "when Priority like 'NON' then 5 " +
            "end")
    fun getAllNotesByPriority():Flow<List<NoteEntity>>

    @Transaction
    @Query("select * from NOTES_TABLE where REMOVED = 1")
    fun getAllTrashedNotes(): Flow<List<NoteEntity>>

    @Transaction
    @Query("select * from NOTES_TABLE where REMOVED = 0 order by Reminding desc")
    fun getAllRemindingNotes(): Flow<List<NoteEntity>>


}