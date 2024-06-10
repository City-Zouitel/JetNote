package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.NoteAndAudioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndAudioDao {

    @Query("SELECT * FROM NOTE_AND_AUDIO")
    fun getAllNotesAndAudios(): Flow<List<NoteAndAudioEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteAndAudio(noteAndAudio: NoteAndAudioEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateNoteAndAudio(noteAndAudio: NoteAndAudioEntity)

    @Delete
    suspend fun deleteNoteAndAudio(noteAndAudio: NoteAndAudioEntity)

}