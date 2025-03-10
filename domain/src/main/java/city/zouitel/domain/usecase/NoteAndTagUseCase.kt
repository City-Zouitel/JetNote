package city.zouitel.domain.usecase

import city.zouitel.domain.model.NoteAndTag
import city.zouitel.domain.repository.NoteAndTagRepository

/**
 * Sealed class representing various use cases for interacting with notes and tags.
 *
 * This class encapsulates different operations that can be performed on [NoteAndTag] entities,
 * providing a type-safe and organized way to access these functionalities. Each subclass represents
 * a specific use case, such as observing, inserting, or deleting note-tag relationships.
 */
sealed class NoteAndTagUseCase {
    /**
     * ObserveAll use case.
     *
     * This use case provides a [Flow] of all [NoteWithTags] objects from the repository.
     * It's a simple wrapper around the repository's `observeAll` method, allowing for
     * a more structured way to access the data stream.
     *
     * @property repo The [NoteAndTagRepository] used to access the underlying data.
     */
    data class ObserveAll(private val repo: NoteAndTagRepository): NoteAndTagUseCase() {
        operator fun invoke() = repo.observeAll
    }
    /**
     * Represents a use case for inserting a [NoteAndTag] into the data source.
     *
     * This use case leverages the [NoteAndTagRepository] to perform the insertion operation.
     *
     * @property repo The repository responsible for interacting with the data source for [NoteAndTag] entities.
     */
    data class Insert(private val repo: NoteAndTagRepository): NoteAndTagUseCase() {
        suspend operator fun invoke(noteAndTag: NoteAndTag) = repo.insert(noteAndTag)
    }

    /**
     *  UseCase for deleting a note and its associated tags by the note's unique identifier (UID).
     *
     *  This use case leverages the [NoteAndTagRepository] to perform the deletion operation.
     *  It's designed to be invoked as a suspending function, suitable for use within coroutines.
     *
     *  @property repos The [NoteAndTagRepository] instance responsible for interacting with the data layer.
     */
    data class DeleteByUid(private val repos: NoteAndTagRepository): NoteAndTagUseCase() {
        suspend operator fun invoke(uid: String) = repos.deleteByUid(uid)
    }

    /**
     * Deletes a Note and its associated Tags by the Note's ID.
     *
     * This use case interacts with the [NoteAndTagRepository] to remove a note
     * and all its related tags from the data source.
     *
     * @property repos The repository responsible for handling note and tag data persistence.
     */
    data class DeleteById(private val repos: NoteAndTagRepository): NoteAndTagUseCase() {
        suspend operator fun invoke(id: Long) = repos.deleteById(id)
    }

    data class DeleteDrafts(private val repo: NoteAndTagRepository): NoteAndTagUseCase() {
        suspend operator fun invoke() = repo.deleteDrafts()
    }
}
