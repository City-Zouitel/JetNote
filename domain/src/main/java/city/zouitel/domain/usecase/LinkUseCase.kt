package city.zouitel.domain.usecase

import city.zouitel.domain.model.Link
import city.zouitel.domain.repository.LinkRepository

//@Singleton
sealed class LinkUseCase {
    class GetAllLinks /*@Inject*/ constructor(
        private val repository: LinkRepository
    ): LinkUseCase() {
        operator fun invoke() = repository.getAllLinks
    }

    class AddLink /*@Inject*/ constructor(
        private val repository: LinkRepository
    ): LinkUseCase() {
        suspend operator fun invoke(link: Link) = repository.addLink(link)
    }

    class DeleteLink /*@Inject*/ constructor(
        private val repository: LinkRepository
    ): LinkUseCase() {
        suspend operator fun invoke(link: Link) = repository.deleteLink(link)
    }
}