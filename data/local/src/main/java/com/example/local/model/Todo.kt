package com.example.local.model

import androidx.room.*
import com.example.local.utils.Constants
import com.example.local.utils.Constants.ID
import com.example.local.utils.Constants.TASKS_TABLE

@Entity(
    tableName = TASKS_TABLE,
//    indices = [Index(ID)],
//    foreignKeys = [
//        ForeignKey(
//            entity = Note::class,
//            parentColumns = arrayOf(Constants.UUID),
//            childColumns = arrayOf(ID),
//            onDelete = ForeignKey.CASCADE
//        )
//    ]
)
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID) val id:Long = 0L,
    var item:String? = null,
    var isDone:Boolean = false
)
