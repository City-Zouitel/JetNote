package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import city.zouitel.database.model.TagEntity
import city.zouitel.database.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface TagDao {

    @Query("SELECT * FROM LABELS_TABLE")
    fun getAllTags(): Flow<List<TagEntity>>

    @Query("SELECT * FROM LABELS_TABLE WHERE ${Constants.ID} = :id")
    suspend fun getTagById(id: Long): TagEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTag(tagEntity: TagEntity)

    @Update
    suspend fun updateTag(tagEntity: TagEntity)

    @Delete
    suspend fun deleteTag(tagEntity: TagEntity)
}