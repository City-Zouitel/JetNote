package com.example.jetnote.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jetnote.db.daos.*
import com.example.jetnote.db.entities.label.Label
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.db.entities.note_and_label.NoteAndLabel
import com.example.jetnote.db.entities.note_and_todo.NoteAndTodo
import com.example.jetnote.db.entities.todo.Todo

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