package com.example.local.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import com.example.local.Cons.DESCRIPTION
import com.example.local.Cons.HOST
import com.example.local.Cons.ID
import com.example.local.Cons.LINK
import com.example.local.Cons.TITLE

@Entity("links_table")
data class Link(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(ID) var id: Long = 0L,
    @ColumnInfo(LINK) var link: String = "",
    @ColumnInfo(HOST) var host: String = "",
    @ColumnInfo(TITLE) val title: String? = "",
    @ColumnInfo(DESCRIPTION) var description: String? = ""
)