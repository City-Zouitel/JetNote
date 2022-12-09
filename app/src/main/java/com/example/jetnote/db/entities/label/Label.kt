package com.example.jetnote.db.entities.label

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetnote.cons.COLOR
import com.example.jetnote.cons.ID

@Entity(tableName = "label")
data class Label(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID) val id:Long = 0L,
    @ColumnInfo(name = "label") val label:String? = null,
    @ColumnInfo(name = COLOR, defaultValue = "0x0000") val color: Int = 0x0000
)