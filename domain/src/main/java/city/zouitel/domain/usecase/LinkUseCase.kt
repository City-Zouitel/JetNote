package city.zouitel.domain.usecase

import city.zouitel.domain.model.Link
import city.zouitel.domain.repository.LinkRepository

/**
 * Sealed class representing various use cases related to [Link] entities.
 * Each data class within this sealed class represents a specific operation
 * that can be performed on the [Link] data, interacting with the [LinkRepository].
 */
sealed class LinkUseCase {
    /**
     * Data class representing a use case for observing all links from the repository.
     *
     * This use case provides a way to retrieve a stream of all link objects
     * stored in the [LinkRepository].  Any changes made to the links in the
     * repository will be reflected in the flow emitted by this use case.
     *
     * @property repo The [LinkRepository] instance used to access the underlying link data.
     */
    data class ObserveAll(private val repo: LinkRepository) : LinkUseCase() {
        operator fun invoke() = repo.observeAll
    }

    /**
     *  UseCase for observing a stream of [Link] objects associated with a specific user ID (UID).
     *
     *  This use case leverages the [LinkRepository] to provide a reactive stream of [Link] data.
     *  It's designed to emit updates whenever the data related to the given UID changes in the repository.
     *
     *  @property repo The [LinkRepository] used to interact with the data source.
     */
    data class ObserveByUid(private val repo: LinkRepository) : LinkUseCase() {
        suspend operator fun invoke(uid: String) = repo.observeByUid(uid)
    }

    /**
     * [LinkUseCase] implementation for inserting a [Link] into the repository.
     *
     * This class provides a suspend function to insert a new link into the persistent storage
     * via the provided [LinkRepository].
     *
     * @property repo The [LinkRepository] instance used to interact with the data storage.
     */
    data class Insert(private val repo: LinkRepository): LinkUseCase() {
        suspend operator fun invoke(link: Link) = repo.insert(link)
    }

    /**
     * `DeleteById` is a use case responsible for deleting a link from the repository by its unique identifier.
     *
     * This use case interacts with a [LinkRepository] to perform the deletion operation.
     *
     * @property repo The [LinkRepository] instance used for data access.
     */
    data class DeleteById(private val repo: LinkRepository) : LinkUseCase() {
        suspend operator fun invoke(id: Int) = repo.deleteById(id)
    }

    /**
     * [LinkUseCase] for deleting a link by its unique identifier (UID).
     *
     * This use case provides a way to remove a link from the data source based on its UID.
     * It interacts with the [LinkRepository] to perform the deletion operation.
     *
     * @property repo The [LinkRepository] responsible for data access.
     */
    data class DeleteByUid(private val repo: LinkRepository) : LinkUseCase() {
        suspend operator fun invoke(uid: String) = repo.deleteByUid(uid)
    }
}