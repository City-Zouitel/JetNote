package com.example.domain.repos

import com.example.local.model.TagEntity
import kotlinx.coroutines.flow.Flow

interface LabelRepo {
    val getAllLabels:Flow<List<TagEntity>>

    suspend fun addLabel(tagEntity: TagEntity)

    suspend fun updateLabel(tagEntity: TagEntity)

    suspend fun deleteLabel(tagEntity: TagEntity)
}