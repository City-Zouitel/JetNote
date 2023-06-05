package com.example.note.model

import androidx.annotation.Keep
import com.example.links.model.Link
import com.example.note.model.Data
import com.example.tags.model.Tag
import com.example.tasks.model.Task

@Keep
data class Note(
    val dataEntity: Data,
    val tagEntities: List<Tag>,
    val taskEntities: List<Task>,
    val linkEntities: List<Link>
)
