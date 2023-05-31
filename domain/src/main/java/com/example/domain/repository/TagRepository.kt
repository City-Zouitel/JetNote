package com.example.domain.repository

import com.example.domain.model.Tag as OutTag
import kotlinx.coroutines.flow.Flow

interface TagRepository {
    val getAllLabels:Flow<List<OutTag>>

    suspend fun addTag(tag: OutTag)

    suspend fun updateTag(tag: OutTag)

    suspend fun deleteTag(tag: OutTag)
}