package com.example.domain.model

data class Task(
    val id:Long = 0L,
    var item:String? = null,
    var isDone:Boolean = false
)
