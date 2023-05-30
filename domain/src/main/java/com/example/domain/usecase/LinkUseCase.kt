package com.example.domain.usecase

import com.example.domain.model.Link
import com.example.domain.repository.LinkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class LinkUseCase {
    class GetAllLinks @Inject constructor(
        private val repo: LinkRepository

    ): LinkUseCase() {
        operator fun invoke() = repo.getAllLinks
    }

    class AddLink @Inject constructor(
        private val repo: LinkRepository
    ): LinkUseCase() {
        suspend operator fun invoke(link: Link) = repo.addLink(link)
    }

    class DeleteLink @Inject constructor(
        private val repo: LinkRepository
    ): LinkUseCase() {
        suspend operator fun invoke(link: Link) = repo.deleteLink(link)
    }
}