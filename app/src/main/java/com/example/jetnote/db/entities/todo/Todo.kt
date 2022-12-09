package com.example.jetnote.db.entities.todo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetnote.cons.ID

@Entity(tableName = "todo")
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID) val id:Long = 0L,
    var item:String? = null,
    var isDone:Boolean = false
)
