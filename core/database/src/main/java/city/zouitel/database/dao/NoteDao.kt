package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import city.zouitel.database.model.relational.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Transaction
    @Query("SELECT * FROM NOTES_TABLE WHERE REMOVED = 0 ORDER BY Date ASC")
    fun getAllNotesById():Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM NOTES_TABLE WHERE REMOVED = 0 ORDER BY Date DESC")
    fun getAllNotesByNewest():Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM NOTES_TABLE WHERE REMOVED = 0 ORDER BY Date ASC")
    fun getAllNotesByOldest():Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM NOTES_TABLE WHERE REMOVED = 0 ORDER BY TITLE ASC")
    fun getAllNotesByName():Flow<List<NoteEntity>>

    @Transaction
@Query("SELECT * FROM NOTES_TABLE WHERE REMOVED = 0 ORDER BY CASE " +
            "WHEN PRIORITY LIKE 'URGENT' THEN 1 " +
            "WHEN PRIORITY LIKE 'IMPORTANT' THEN 2 " +
            "WHEN PRIORITY LIKE 'NORMAL' THEN 3 " +
            "WHEN PRIORITY LIKE 'LOW' THEN 4 " +
            "WHEN PRIORITY LIKE 'NON' THEN 5 " +
            "END")
    fun getAllNotesByPriority():Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM NOTES_TABLE WHERE REMOVED = 1")
    fun getAllTrashedNotes(): Flow<List<NoteEntity>>

    @Transaction
    @Query("SELECT * FROM NOTES_TABLE WHERE REMOVED = 0 ORDER BY REMINDING DESC")
    fun getAllRemindingNotes(): Flow<List<NoteEntity>>


}