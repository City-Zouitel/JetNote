package city.zouitel.database.dao

import androidx.room.*
import city.zouitel.database.model.NoteAndTagEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndTagDao {

    @Query("SELECT * FROM NOTE_AND_LABEL")
    fun getAllNotesAndTags():Flow<List<NoteAndTagEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteAndTag(noteAndLabel: NoteAndTagEntity)

    @Delete
    suspend fun deleteNoteAndTag(noteAndLabel: NoteAndTagEntity)
}