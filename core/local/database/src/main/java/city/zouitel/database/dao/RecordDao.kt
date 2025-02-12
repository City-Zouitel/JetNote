package city.zouitel.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import city.zouitel.database.model.Record
import city.zouitel.database.model.Record.Companion.TABLE_NAME
import city.zouitel.database.utils.Constants.ID
import city.zouitel.database.utils.Constants.PATH
import city.zouitel.database.utils.Constants.UUID
import kotlinx.coroutines.flow.Flow

@Dao
interface RecordDao {
    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAll: Flow<List<Record>>

    @Query("SELECT * FROM $TABLE_NAME WHERE $UUID = :uid")
    fun observeByUid(uid: String): Flow<List<Record>>

    @Upsert
    suspend fun insert(record: Record)

    @Query("DELETE FROM $TABLE_NAME WHERE $ID = :id OR $PATH = :path")
    suspend fun delete(id: Long, path: String)

    @Query("DELETE FROM $TABLE_NAME WHERE $UUID = :uid")
    suspend fun delete(uid: String)
}