package city.zouitel.domain.usecase

import city.zouitel.domain.model.Link
import city.zouitel.domain.repository.LinkRepository

sealed class LinkUseCase {
    data class ObserveAll(private val repo: LinkRepository) : LinkUseCase() {
        operator fun invoke() = repo.observeAll
    }

    data class ObserveByUid(private val repo: LinkRepository) : LinkUseCase() {
        suspend operator fun invoke(uid: String) = repo.observeByUid(uid)
    }

    data class Insert(private val repo: LinkRepository): LinkUseCase() {
        suspend operator fun invoke(link: Link) = repo.insert(link)
    }

    data class DeleteByUid(private val repo: LinkRepository) : LinkUseCase() {
        suspend operator fun invoke(uid: String) = repo.deleteByUid(uid)
    }
}