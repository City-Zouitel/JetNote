package city.zouitel.generativeai.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import city.zouitel.generativeai.model.Message
import city.zouitel.generativeai.model.Message.Companion.TABLE_NAME
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {

    @get:Query("SELECT * FROM $TABLE_NAME")
    val observeAllMessages: Flow<List<Message>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(message: Message)
}