package com.example.local.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.util.TableInfo
import com.example.local.utils.Constants.DESCRIPTION
import com.example.local.utils.Constants.HOST
import com.example.local.utils.Constants.ID
import com.example.local.utils.Constants.IMG_LINK
import com.example.local.utils.Constants.TITLE
import com.example.local.utils.Constants.URL

@Entity("links_table")
data class Link(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(ID) var id: String = "",
    @ColumnInfo(URL) var url: String = "",
    @ColumnInfo(HOST) var host: String = "",
    @ColumnInfo(IMG_LINK) var image: String? = "",
    @ColumnInfo(TITLE) val title: String? = "",
    @ColumnInfo(DESCRIPTION) var description: String? = ""
)