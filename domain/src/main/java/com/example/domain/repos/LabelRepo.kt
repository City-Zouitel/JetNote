package com.example.domain.repos

import com.example.local.model.Label
import kotlinx.coroutines.flow.Flow

interface LabelRepo {
    val getAllLabels:Flow<List<Label>>

    suspend fun addLabel(label: Label)

    suspend fun updateLabel(label: Label)

    suspend fun deleteLabel(label: Label)
}