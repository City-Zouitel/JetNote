package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import city.zouitel.database.model.Link
import city.zouitel.database.model.Link.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

@Dao
interface LinkDao {

    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Link>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<List<Link>>

    @Upsert(entity = Link::class)
    suspend fun insert(link: Link)

    @Query("DELETE FROM $TABLE_NAME WHERE $UUID = :uid")
    suspend fun deleteByUid(uid: String)
}