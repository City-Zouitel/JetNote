package com.example.jetnote.db.entities.note_and_todo

import androidx.room.Entity

@Entity(
    tableName = "note_and_todo",
    primaryKeys = ["noteUid","todoId"]
)
data class NoteAndTodo(
    val noteUid:String,
    val todoId:Long
)
