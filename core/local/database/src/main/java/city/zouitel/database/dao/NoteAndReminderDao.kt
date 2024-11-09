package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.NoteAndReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndReminderDao {

    @get:Query("SELECT * FROM NOTE_AND_REMINDER")
    val observeAllNotesAndReminders: Flow<List<NoteAndReminderEntity>>

    @Query("SELECT * FROM NOTE_AND_REMINDER WHERE noteId = :noteId")
    fun observeNoteAndRemindersById(noteId: String): Flow<List<NoteAndReminderEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteAndReminder(noteAndReminder: NoteAndReminderEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNoteAndReminder(noteAndReminders: NoteAndReminderEntity)

    @Query("DELETE FROM NOTE_AND_REMINDER")
    suspend fun deleteAllNotesAndReminders()

    @Query("DELETE FROM NOTE_AND_REMINDER WHERE noteId = :noteId")
    suspend fun deleteNoteAndReminderById(noteId: String)
}
