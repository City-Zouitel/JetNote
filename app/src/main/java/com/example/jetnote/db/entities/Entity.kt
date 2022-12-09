package com.example.jetnote.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.jetnote.cons.ID
import com.example.jetnote.cons.UID
import com.example.jetnote.db.entities.label.Label
import com.example.jetnote.db.entities.note.Note
import com.example.jetnote.db.entities.note_and_label.NoteAndLabel
import com.example.jetnote.db.entities.note_and_todo.NoteAndTodo
import com.example.jetnote.db.entities.todo.Todo

data class Entity(
    @Embedded val note: Note,
    @Relation(
        parentColumn = UID,
        entityColumn = ID,
        entity = Label::class,
        associateBy = Junction(
            NoteAndLabel::class,
            parentColumn = "noteUid",
            entityColumn = "labelId"
        )
    )
    val labels :List<Label>,
    @Relation(
        parentColumn = UID,
        entityColumn = ID,
        entity = Todo::class,
        associateBy = Junction(
            NoteAndTodo::class,
            parentColumn = "noteUid",
            entityColumn = "todoId"
        )
    )
    val todoItems:List<Todo>
)
