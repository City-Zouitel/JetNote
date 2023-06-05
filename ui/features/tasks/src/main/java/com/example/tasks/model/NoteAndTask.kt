package com.example.tasks.model

import androidx.annotation.Keep

@Keep
data class NoteAndTask(
    val noteUid:String,
    val todoId:Long
)