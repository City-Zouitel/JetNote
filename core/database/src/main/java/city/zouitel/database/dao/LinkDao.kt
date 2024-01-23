package city.zouitel.database.dao

import androidx.room.*
import city.zouitel.database.model.LinkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @Query("select * from links_table")
    fun getAllLinks(): Flow<List<LinkEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLink(link: LinkEntity)

    @Delete
    suspend fun deleteLink(link: LinkEntity)

}