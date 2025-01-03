package city.zouitel.database

import androidx.room.Database
import androidx.room.RoomDatabase
import city.zouitel.database.dao.AudioDao
import city.zouitel.database.dao.DataDao
import city.zouitel.database.dao.LinkDao
import city.zouitel.database.dao.MediaDao
import city.zouitel.database.dao.NoteAndAudioDao
import city.zouitel.database.dao.NoteAndLinkDao
import city.zouitel.database.dao.NoteAndTagDao
import city.zouitel.database.dao.NoteDao
import city.zouitel.database.dao.ReminderDao
import city.zouitel.database.dao.TagDao
import city.zouitel.database.dao.TaskDao
import city.zouitel.database.dao.WidgetDao
import city.zouitel.database.model.AudioEntity
import city.zouitel.database.model.DataEntity
import city.zouitel.database.model.LinkEntity
import city.zouitel.database.model.Media
import city.zouitel.database.model.NoteAndAudioEntity
import city.zouitel.database.model.NoteAndLinkEntity
import city.zouitel.database.model.NoteAndTagEntity
import city.zouitel.database.model.Reminder
import city.zouitel.database.model.TagEntity
import city.zouitel.database.model.Task

@Database(
    version = 3,
    autoMigrations = [],
    entities = [
        DataEntity::class,
        TagEntity::class,
        NoteAndTagEntity::class,
        Task::class,
        LinkEntity::class,
        NoteAndLinkEntity::class,
        AudioEntity::class,
        NoteAndAudioEntity::class,
        Media::class,
        Reminder::class
    ],
    exportSchema = true
)
abstract class Database: RoomDatabase() {
    abstract fun getNoteDao(): DataDao
    abstract fun getLabelDao(): TagDao
    abstract fun getNoteAndLabelDao(): NoteAndTagDao
    abstract fun getEntityDao(): NoteDao
    abstract fun getWidgetEntityDao(): WidgetDao
    abstract fun getTaskDao(): TaskDao
    abstract fun getLinkDao(): LinkDao
    abstract fun getNoteAndLinkDao(): NoteAndLinkDao
    abstract fun getAudioDao(): AudioDao
    abstract fun getNoteAndAudioDao(): NoteAndAudioDao
    abstract fun getMediaDao(): MediaDao
    abstract fun getReminderDao(): ReminderDao
}