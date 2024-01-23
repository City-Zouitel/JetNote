package city.zouitel.database

import androidx.room.Database
import androidx.room.RoomDatabase
import city.zouitel.database.dao.*
import city.zouitel.database.model.*

@Database(
    version = 1,
    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
                     ],
    entities = [
        DataEntity::class,
        TagEntity::class,
        NoteAndTagEntity::class,
        TaskEntity::class,
        NoteAndTaskEntity::class,
        LinkEntity::class,
        NoteAndLinkEntity::class
               ],
    exportSchema = true
)
abstract class Database:RoomDatabase() {
    abstract fun getNoteDao(): DataDao
    abstract fun getLabelDao(): TagDao
    abstract fun getNoteAndLabelDao(): NoteAndTagDao
    abstract fun getEntityDao(): NoteDao
    abstract fun getWidgetEntityDao(): WidgetDao
    abstract fun getTodoDao(): TaskDao
    abstract fun getNoteAndTodoDao(): NoteAndTaskDao
    abstract fun getLinkDao(): LinkDao
    abstract fun getNoteAndLink(): NoteAndLinkDao
}