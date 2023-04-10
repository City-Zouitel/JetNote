package com.example.domain.reposImpl

import com.example.domain.repos.LinkRepo
import com.example.local.daos.LinkDao
import com.example.local.model.Link
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class LinkRepoImpl @Inject constructor(
    private val dao: LinkDao
): LinkRepo {
    override suspend fun addLink(link: Link) {

    }

    override suspend fun deleteLink(link: Link) {
    }
}