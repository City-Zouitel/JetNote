package com.example.domain.repos

import com.example.local.model.Link

interface LinkRepo {

    suspend fun addLink(link: Link)

    suspend fun deleteLink(link: Link)
}