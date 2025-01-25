package city.zouitel.domain.usecase

import city.zouitel.domain.model.Audio
import city.zouitel.domain.repository.AudioRepo

/**
 * Sealed class representing various use cases for interacting with audio data.
 * Each subclass represents a specific operation that can be performed on the audio data.
 *
 * All use cases rely on an instance of [AudioRepo] for data access.
 */
sealed class AudioUseCase {
    /**
     * [ObserveAll] is a use case that provides a stream of all [Audio] objects
     * from the underlying [AudioRepo].
     *
     * This use case is typically used to observe changes in the entire collection
     * of audio data and react accordingly.  For instance, a UI component might
     * collect this flow to display a list of all available audio tracks and update
     * whenever the database is changed.
     *
     * @property repo The [AudioRepo] responsible for managing audio data persistence.
     */
    data class ObserveAll(private val repo: AudioRepo): AudioUseCase() {
        operator fun invoke() = repo.observeAll
    }

    /**
     * [AudioUseCase] implementation that observes changes to an audio item identified by its unique ID (UID).
     *
     * This use case retrieves a [Flow] of [Audio] objects from the [AudioRepo] that represents the
     * audio item with the specified [uid]. Any changes to the audio item in the repository will be emitted
     * through this flow.
     *
     * @property repo The [AudioRepo] used to access and observe audio data.
     */
    data class ObserveByUid(private val repo: AudioRepo): AudioUseCase() {
        operator fun invoke(uid: String) = repo.observeByUid(uid)
    }

    /**
     * [Insert] is a use case responsible for inserting an [Audio] object into the data source.
     *
     * This class encapsulates the logic for adding a new audio entry to the underlying repository.
     * It leverages the [AudioRepo] to perform the actual data insertion.
     *
     * @property repo The [AudioRepo] instance used to interact with the data source.
     */
    data class Insert(private val repo: AudioRepo): AudioUseCase() {
        suspend operator fun invoke(audio: Audio) = repo.insert(audio)
    }

    /**
     * [DeleteByUid] is a use case responsible for deleting an audio item from the data source
     * based on its unique identifier (UID).
     *
     * This use case interacts with the [AudioRepo] to perform the deletion operation.
     *
     * @property repo The [AudioRepo] instance used for interacting with the underlying data source.
     */
    data class DeleteByUid(private val repo: AudioRepo): AudioUseCase() {
        suspend operator fun invoke(uid: String) = repo.deleteByUid(uid)
    }

    /**
     * [DeleteById] is a use case responsible for deleting an audio item by its ID.
     *
     * This class encapsulates the logic of deleting an audio item from the underlying
     * data source, abstracting away the specific implementation details of the
     * [AudioRepo].
     *
     * @property repo The [AudioRepo] instance used to interact with the audio data source.
     */
    data class DeleteById(private val repo: AudioRepo): AudioUseCase() {
        suspend operator fun invoke(id: Long) = repo.deleteById(id)
    }
}