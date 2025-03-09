package city.zouitel.domain.usecase

import city.zouitel.domain.model.Media
import city.zouitel.domain.repository.MediaRepository

/**
 * Sealed class representing different use cases for interacting with media data.
 * Each subclass represents a specific operation that can be performed on the media repository.
 *
 * This class promotes type safety and allows for exhaustive when statements when handling
 * different media operations.
 */
sealed class MediaUseCase {
    /**
     * [ObserveAll] is a use case that provides a stream of all media items from the [MediaRepository].
     *
     * This use case is designed to observe changes in the entire media collection.
     * It retrieves a Flow that emits a list of [Media] objects whenever the underlying data in the [MediaRepository] is updated.
     *
     * @property repo The [MediaRepository] used as the data source for media items.
     * @constructor Creates an instance of [ObserveAll] with the provided [MediaRepository].
     *
     * @return A Flow emitting a list of all [Media] items.
     */
    data class ObserveAll(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke() = repo.observeAll
    }

    /**
     * `ObserveByUid` is a [MediaUseCase] that provides a stream of medias associated with a given UID.
     *
     * This use case leverages the [MediaRepository] to observe changes to media items filtered by a specific UID.
     *
     * @property repo The [MediaRepository] used to interact with the underlying data source.
     */
    data class ObserveByUid(private val repo: MediaRepository): MediaUseCase() {
        suspend operator fun invoke(uid: String) = repo.observeByUid(uid)
    }

    /**
     * [Insert] is a [MediaUseCase] responsible for inserting a new [Media] item into the data source.
     *
     * This use case delegates the actual insertion operation to the provided [MediaRepository].
     *
     * @property repo The [MediaRepository] instance used to interact with the media data source.
     */
    data class Insert(private val repo: MediaRepository): MediaUseCase() {
        suspend operator fun invoke(media: Media) = repo.insert(media)
    }

    /**
     * [MediaUseCase] implementation to delete a media item by its ID.
     *
     * This use case delegates the actual deletion to the provided [MediaRepository].
     *
     * @property repo The [MediaRepository] used for data access.
     */
    data class DeleteById(private val repo: MediaRepository): MediaUseCase() {
        suspend operator fun invoke(id: Long) = repo.deleteById(id)
    }

    data class GetDrafts(private val repo: MediaRepository): MediaUseCase() {
        suspend operator fun invoke() = repo.getDrafts()
    }

    data class DeleteDrafts(private val repo: MediaRepository): MediaUseCase() {
        suspend operator fun invoke() = repo.deleteDrafts()
    }
}