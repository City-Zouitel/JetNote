package city.zouitel.domain.usecase

import city.zouitel.domain.model.Media
import city.zouitel.domain.repository.MediaRepository

sealed class MediaUseCase {

    class GetAllMedias(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke() = repo.getAllMedias
    }

    class AddMedia(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke(media: Media) = repo.addMedia(media)
    }

    class UpdateMedia(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke(media: Media) = repo.updateMedia(media)
    }

    class DeleteMedia(private val repo: MediaRepository): MediaUseCase() {
        operator fun invoke(media: Media) = repo.deleteMedia(media)
    }
}