package com.example.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.dao.*
import com.example.local.model.*

@Database(
    version = 1,
    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
                     ],
    entities = [
        Note::class,
        Label::class,
        NoteAndLabel::class,
        Todo::class,
        NoteAndTodo::class,
        Link::class,
        NoteAndLink::class
               ],
    exportSchema = true
)
abstract class Database:RoomDatabase() {
    abstract fun getNoteDao(): NotesDao
    abstract fun getLabelDao(): LabelDao
    abstract fun getNoteAndLabelDao(): NoteAndLabelDao
    abstract fun getEntityDao(): EntityDao
    abstract fun getWidgetEntityDao(): WidgetEntityDao
    abstract fun getTodoDao(): TodoDao
    abstract fun getNoteAndTodoDao(): NoteAndTodoDao
    abstract fun getLinkDao(): LinkDao
    abstract fun getNoteAndLink(): NoteAndLinkDao
}