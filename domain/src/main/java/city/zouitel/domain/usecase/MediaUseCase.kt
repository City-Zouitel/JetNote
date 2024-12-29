package city.zouitel.domain.usecase

import city.zouitel.domain.model.Media
import city.zouitel.domain.repository.MediaRepository

sealed class MediaUseCase {
    data class ObserveAll(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke() = repo.observeAll
    }

    data class ObserveByUid(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke(uid: String) = repo.observeByUid(uid)
    }

    data class Insert(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke(media: Media) = repo.insert(media)
    }

    data class UpdateMedia(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke(media: Media) = repo.updateMedia(media)
    }

    data class DeleteById(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke(id: Long) = repo.deleteById(id)
    }
}