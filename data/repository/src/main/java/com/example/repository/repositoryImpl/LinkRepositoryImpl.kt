package com.example.repository.repositoryImpl

import com.example.domain.model.Link
import com.example.domain.repository.LinkRepository
import com.example.repository.datasource.LinkDataSource
import com.example.repository.mapper.LinkMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LinkRepositoryImpl @Inject constructor(
    private val linkMapper: LinkMapper,
    private val linkDataSource: LinkDataSource
): LinkRepository {
    override val getAllLinks: Flow<List<Link>>
        get() = linkDataSource.getAllLinks.map { list ->
            list.map {
                linkMapper.toDomain(it)
            }
        }

    override suspend fun addLink(link: Link) {
        linkDataSource.addLink(linkMapper.toRepository(link))
    }

    override suspend fun deleteLink(link: Link) {
        linkDataSource.deleteLink(linkMapper.toRepository(link))
    }
}