package com.example.local.model

import androidx.room.Entity

@Entity(
    tableName = "note_and_label",
    primaryKeys = ["noteUid", "labelId"]
)
data class NoteAndLabel(
    val noteUid: String,
    val labelId: Long
)