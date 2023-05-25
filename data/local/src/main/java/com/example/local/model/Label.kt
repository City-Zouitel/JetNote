package com.example.local.model

import androidx.room.*
import com.example.local.utils.Constants
import com.example.local.utils.Constants.COLOR
import com.example.local.utils.Constants.ID
import com.example.local.utils.Constants.UID

@Entity(
    tableName = "label",
    indices = [Index(ID)],
    foreignKeys = [
        ForeignKey(
            entity = Note::class,
            parentColumns = arrayOf(UID),
            childColumns = arrayOf(ID),
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Label(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID) val id:Long = 0L,
    @ColumnInfo(name = "label") val label:String? = null,
    @ColumnInfo(name = COLOR, defaultValue = "0x0000") val color: Int = 0x0000
)