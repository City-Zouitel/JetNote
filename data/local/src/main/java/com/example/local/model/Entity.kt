package com.example.local.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.local.Cons.ID
import com.example.local.Cons.UID

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
    val todoItems:List<Todo>,
    @Relation(
        parentColumn = UID,
        entityColumn = ID,
        entity = Link::class,
        associateBy = Junction(
            NoteAndLink::class,
            parentColumn = "noteUid",
            entityColumn = "linkId"
        )
    )
    val links: List<Link>
)
