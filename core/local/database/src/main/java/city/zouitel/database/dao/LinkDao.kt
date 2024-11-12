package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.database.model.LinkEntity
import city.zouitel.database.utils.Constants
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Query("SELECT * FROM LINKS_TABLE")
    fun getAllLinks(): Flow<List<LinkEntity>>

    @Query("SELECT * FROM LINKS_TABLE WHERE ${Constants.ID} = :id")
    suspend fun getLinkById(id: Long): LinkEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLink(link: LinkEntity)

    @Delete
    suspend fun deleteLink(link: LinkEntity)
}