package com.example.local.model

import androidx.room.Entity

@Entity(
    tableName = "note_and_label",
    primaryKeys = ["noteUid", "labelId"]
)
data class NoteAndTagEntity(
    val noteUid: String,
    val labelId: Long
)