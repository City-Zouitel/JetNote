package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.MediaEntity
import city.zouitel.database.model.NoteAndMediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteAndMediaDao {

    @Query("SELECT * FROM NOTE_AND_MEDIA")
    fun getAllMedias(): Flow<List<NoteAndMediaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNoteAndMedia(noteAndMedia: NoteAndMediaEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateNoteAndMedia(noteAndMedia: NoteAndMediaEntity)

    @Delete
    fun deleteNoteAndMedia(noteAndMedia: NoteAndMediaEntity)
}