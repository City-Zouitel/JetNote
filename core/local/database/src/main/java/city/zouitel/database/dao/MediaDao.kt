package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.Media
import city.zouitel.database.model.Media.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

@Dao
interface MediaDao {

    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Media>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<List<Media>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(media: Media)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateMedia(media: Media)

    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id")
    fun deleteById(id: Long)
}