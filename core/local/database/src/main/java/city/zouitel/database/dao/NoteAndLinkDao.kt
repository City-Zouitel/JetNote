package city.zouitel.database.dao

import androidx.room.*
import city.zouitel.database.model.NoteAndLinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndLinkDao {

    @Query("SELECT * FROM NOTE_AND_LINK")
    fun getAllNotesAndLinks(): Flow<List<NoteAndLinkEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteAndLink(noteAndLink: NoteAndLinkEntity)

    @Delete
    suspend fun deleteNoteAndLink(noteAndLink: NoteAndLinkEntity)
}