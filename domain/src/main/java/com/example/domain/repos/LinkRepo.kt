package com.example.domain.repos

import com.example.local.model.Link
import kotlinx.coroutines.flow.Flow

interface LinkRepo {

    val getAllLinks: Flow<List<Link>>

    suspend fun addLink(link: Link)

    suspend fun deleteLink(link: Link)
}