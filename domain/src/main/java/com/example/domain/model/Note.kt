package com.example.domain.model

data class Note(
    val dataEntity: Data,
    val tagEntities: List<Tag>,
    val taskEntities: List<Task>,
    val linkEntities: List<Link>
)
