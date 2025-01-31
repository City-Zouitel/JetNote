package city.zouitel.domain.usecase

import city.zouitel.domain.model.Tag
import city.zouitel.domain.repository.TagRepository

/**
 * Sealed class representing various use cases for interacting with tags.
 * This class encapsulates different operations that can be performed on [Tag] entities
 * through a [TagRepository].
 */
sealed class TagUseCase {
    /**
     *  UseCase to observe all tags from the repository.
     *
     *  This use case retrieves a [Flow] that emits a list of all [Tag]s
     *  present in the underlying [TagRepository].  It acts as a wrapper
     *  around [TagRepository.observeAll], providing a cleaner interface for
     *  the domain layer to interact with.
     *
     *  @property repo The [TagRepository] instance used to access the tag data.
     */
    data class ObserveAll(private val repo: TagRepository): TagUseCase() {
        operator fun invoke() = repo.observeAll
    }

    /**
     * Use case for inserting a new [Tag] into the repository.
     *
     * This use case encapsulates the logic for inserting a tag, delegating the actual data
     * operation to the provided [TagRepository].
     *
     * @property repo The [TagRepository] instance responsible for persisting tag data.
     */
    data class Insert(private val repo: TagRepository): TagUseCase() {
        suspend operator fun invoke(tag: Tag) = repo.insert(tag)
    }

    /**
     * [DeleteById] is a use case that deletes a tag from the data source by its unique identifier.
     *
     * This class encapsulates the logic for deleting a tag, interacting with the [TagRepository] to perform
     * the actual deletion. It is designed to be executed within a coroutine scope.
     *
     * @property repo The [TagRepository] instance used to interact with the data source for tag operations.
     */
    class DeleteById(private val repo: TagRepository): TagUseCase() {
        suspend operator fun invoke(id: Long) = repo.deleteById(id)
    }
}