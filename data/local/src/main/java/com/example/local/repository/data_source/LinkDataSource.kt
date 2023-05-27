package com.example.local.repository.data_source

import com.example.local.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkDataSource {
    val getAllLinks: Flow<List<Link>>

    suspend fun addLink(link: Link)

    suspend fun deleteLink(link: Link)
}