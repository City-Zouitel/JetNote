package com.example.local

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.local.daos.*
import com.example.local.model.Label
import com.example.local.model.Note
import com.example.local.model.NoteAndLabel
import com.example.local.model.NoteAndTodo
import com.example.local.model.Todo

@Database(
    version = 1,
    autoMigrations = [
//        AutoMigration(from = 1, to = 2)
                     ],
    entities = [
        NoteAndLabel::class,
        NoteAndTodo::class,
        Note::class,
        Label::class,
        Todo::class
               ],
    exportSchema = true
)
abstract class Database:RoomDatabase() {
    abstract fun getNoteDao(): NotesDao
    abstract fun getLabelDao(): LabelDao
    abstract fun getNoteAndLabelDao(): NoteAndLabelDao
    abstract fun getEntityDao(): EntityDao
    abstract fun getTodoDao(): TodoDao
    abstract fun getNoteAndTodoDao(): NoteAndTodoDao
}