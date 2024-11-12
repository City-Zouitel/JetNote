package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.DataEntity

@Dao
interface DataDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addNote(data: DataEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editNote(data: DataEntity)

    @Delete
    suspend fun deleteNote(data: DataEntity)

    @Query("DELETE FROM NOTES_TABLE WHERE REMOVED = 1")
    suspend fun deleteAllTrashedNotes()
}