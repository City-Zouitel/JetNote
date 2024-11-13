package city.zouitel.database

import androidx.room.Database
import androidx.room.RoomDatabase
import city.zouitel.database.dao.AudioDao
import city.zouitel.database.dao.DataDao
import city.zouitel.database.dao.LinkDao
import city.zouitel.database.dao.MediaDao
import city.zouitel.database.dao.NoteAndAudioDao
import city.zouitel.database.dao.NoteAndLinkDao
import city.zouitel.database.dao.NoteAndMediaDao
import city.zouitel.database.dao.NoteAndTagDao
import city.zouitel.database.dao.NoteAndTaskDao
import city.zouitel.database.dao.NoteDao
import city.zouitel.database.dao.ReminderDao
import city.zouitel.database.dao.TagDao
import city.zouitel.database.dao.TaskDao
import city.zouitel.database.dao.WidgetDao
import city.zouitel.database.model.AudioEntity
import city.zouitel.database.model.DataEntity
import city.zouitel.database.model.LinkEntity
import city.zouitel.database.model.MediaEntity
import city.zouitel.database.model.NoteAndAudioEntity
import city.zouitel.database.model.NoteAndLinkEntity
import city.zouitel.database.model.NoteAndMediaEntity
import city.zouitel.database.model.NoteAndTagEntity
import city.zouitel.database.model.NoteAndTaskEntity
import city.zouitel.database.model.ReminderEntity
import city.zouitel.database.model.TagEntity
import city.zouitel.database.model.TaskEntity

@Database(
    version = 1,
    autoMigrations = [],
    entities = [
        DataEntity::class,
        TagEntity::class,
        NoteAndTagEntity::class,
        TaskEntity::class,
        NoteAndTaskEntity::class,
        LinkEntity::class,
        NoteAndLinkEntity::class,
        AudioEntity::class,
        NoteAndAudioEntity::class,
        MediaEntity::class,
        NoteAndMediaEntity::class,
        ReminderEntity::class
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
    abstract fun getNoteAndLinkDao(): NoteAndLinkDao
    abstract fun getAudioDao(): AudioDao
    abstract fun getNoteAndAudioDao(): NoteAndAudioDao
    abstract fun getMediaDao(): MediaDao
    abstract fun getNoteAndMediaDao(): NoteAndMediaDao
    abstract fun getReminderDao(): ReminderDao
}