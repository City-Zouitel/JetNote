package city.zouitel.generativeai

import androidx.room.Database
import androidx.room.RoomDatabase
import city.zouitel.generativeai.dao.MessageDao
import city.zouitel.generativeai.model.Message

@Database(
    version = 1,
    entities = [Message::class],
    exportSchema = true
)
abstract class Cache: RoomDatabase() {
    abstract fun getCacheDao(): MessageDao
}

