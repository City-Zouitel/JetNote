package city.zouitel.database

import androidx.room.Database
import androidx.room.RoomDatabase
import city.zouitel.database.dao.AudioDao
import city.zouitel.database.dao.DataDao
import city.zouitel.database.dao.LinkDao
import city.zouitel.database.dao.MediaDao
import city.zouitel.database.dao.NoteAndTagDao
import city.zouitel.database.dao.NoteDao
import city.zouitel.database.dao.ReminderDao
import city.zouitel.database.dao.TagDao
import city.zouitel.database.dao.TaskDao
import city.zouitel.database.model.Audio
import city.zouitel.database.model.Data
import city.zouitel.database.model.Link
import city.zouitel.database.model.Media
import city.zouitel.database.model.NoteAndTag
import city.zouitel.database.model.Reminder
import city.zouitel.database.model.Tag
import city.zouitel.database.model.Task

@Database(
    version = 1,
    autoMigrations = [],
    entities = [
        Data::class,
        Tag::class,
        NoteAndTag::class,
        Task::class,
        Link::class,
        Audio::class,
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
    abstract fun getTaskDao(): TaskDao
    abstract fun getLinkDao(): LinkDao
    abstract fun getAudioDao(): AudioDao
    abstract fun getMediaDao(): MediaDao
    abstract fun getReminderDao(): ReminderDao
}