package com.example.repository.datasource

import com.example.repository.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkDataSource {

    val getAllLinks: Flow<List<Link>>

    suspend fun addLink(link: Link)

    suspend fun deleteLink(link: Link)
}