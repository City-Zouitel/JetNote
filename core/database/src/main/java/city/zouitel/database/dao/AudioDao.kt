package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.AudioEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AudioDao {

    @Query("SELECT * FROM AUDIOS_TABLE")
    fun getAllAudios(): Flow<List<AudioEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAudio(audioEntity: AudioEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateAudio(audioEntity: AudioEntity)

    @Delete
    suspend fun deleteAudio(audioEntity: AudioEntity)
}