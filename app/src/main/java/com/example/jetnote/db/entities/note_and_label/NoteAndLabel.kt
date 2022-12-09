package com.example.jetnote.db.entities.note_and_label

import androidx.room.Entity

@Entity(
    tableName = "note_and_label",
    primaryKeys = ["noteUid","labelId"]
)
data class NoteAndLabel(
    val noteUid: String,
    val labelId:Long
)