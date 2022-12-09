package com.example.jetnote.db.entities.note

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetnote.cons.*

@Entity(tableName = NOTE_TABLE_NAME)
data class Note(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = UID) var uid: String = "",
    @ColumnInfo(name = TITLE) var title: String? = null,
    @ColumnInfo(name = DESCRIPTION) var description: String? = null ,
    @ColumnInfo(name = PRIORITY) var priority: String = NON,
    @ColumnInfo(name = COLOR) var color: Int = 0,
    @ColumnInfo(name = TEXT_COLOR) var textColor: Int = 0x000000,
    @ColumnInfo(name = DATE) var date: String = "",
    @ColumnInfo(name = TRASHED) var trashed: Int = 0,
    @ColumnInfo(name = AUDIO_DURATION) var audioDuration: Int = 0,
    @ColumnInfo(name = REMINDING) var reminding: Long = 0L,
    @ColumnInfo(name = IMAGE_UIL) var imageUrl: String? = null,
    @ColumnInfo(name = AUDIO_URL) var audioUrl: String? = null,
)
