package city.zouitel.domain.usecase

import city.zouitel.domain.model.Link
import city.zouitel.domain.repository.LinkRepository

//@Singleton
sealed class LinkUseCase {
    class GetAllLinks(
        private val repository: LinkRepository
    ): LinkUseCase() {
        operator fun invoke() = repository.getAllLinks
    }

    class AddLink(
        private val repository: LinkRepository
    ): LinkUseCase() {
        suspend operator fun invoke(link: Link) = repository.addLink(link)
    }

    class DeleteLink(
        private val repository: LinkRepository
    ): LinkUseCase() {
        suspend operator fun invoke(link: Link) = repository.deleteLink(link)
    }
}