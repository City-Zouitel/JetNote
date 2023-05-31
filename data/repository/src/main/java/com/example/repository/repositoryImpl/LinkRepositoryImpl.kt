package com.example.repository.repositoryImpl

import com.example.domain.model.Link as OutLink
import com.example.domain.repository.LinkRepository
import com.example.repository.datasource.LinkDataSource
import com.example.repository.mapper.LinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LinkRepositoryImpl @Inject constructor(
    private val dataSource: LinkDataSource,
    private val mapper: LinkMapper
): LinkRepository {
    override val getAllLinks: Flow<List<OutLink>>
        get() = dataSource.getAllLinks.map { list ->
            list.map { link ->
                mapper.readOnly(link)
            }
        }

    override suspend fun addLink(link: OutLink) {
        dataSource.addLink(mapper.toRepository(link))
    }

    override suspend fun deleteLink(link: OutLink) {
        dataSource.deleteLink(mapper.toRepository(link))
    }
}