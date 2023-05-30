package com.example.local.model

import androidx.room.*
import com.example.local.utils.Constants.COLOR
import com.example.local.utils.Constants.ID
import com.example.local.utils.Constants.LABEL
import com.example.local.utils.Constants.LABELS_TABLE

@Entity(
    tableName = LABELS_TABLE
)
data class TagEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID) val id:Long = 0L,
    @ColumnInfo(name = LABEL) val label:String? = null,
    @ColumnInfo(name = COLOR, defaultValue = "0x0000") val color: Int = 0x0000
)