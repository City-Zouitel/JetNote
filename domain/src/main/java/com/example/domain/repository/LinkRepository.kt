package com.example.domain.repository

import com.example.domain.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkRepository {
    val getAllLinks: Flow<List<Link>>

    suspend fun addLink(link: Link)

    suspend fun deleteLink(link: Link)
}