package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.MediaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @Query("SELECT * FROM MEDIA_TABLE")
    fun getAllMedias(): Flow<List<MediaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMedia(media: MediaEntity)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMedia(media: MediaEntity)

    @Delete
    fun deleteMedia(media: MediaEntity)
}