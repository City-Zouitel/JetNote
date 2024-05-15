package city.zouitel.database.dao

import androidx.room.*
import city.zouitel.database.model.LinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Query("SELECT * FROM LINKS_TABLE")
    fun getAllLinks(): Flow<List<LinkEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLink(link: LinkEntity)

    @Delete
    suspend fun deleteLink(link: LinkEntity)

}