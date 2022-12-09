package com.example.jetnote.repos

import com.example.jetnote.db.entities.label.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepo {
    val getAllLabels:Flow<List<Label>>

    suspend fun addLabel(label: Label)

    suspend fun updateLabel(label: Label)

    suspend fun deleteLabel(label: Label)
}