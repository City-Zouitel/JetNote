package city.zouitel.domain.usecase

import city.zouitel.domain.model.Audio
import city.zouitel.domain.repository.AudioRepository

sealed class AudioUseCase {
    class GetAllAudios(
        private val repository: AudioRepository
    ): AudioUseCase() {
        operator fun invoke() = repository.getAllAudios
    }

    class AddAudio(
        private val repository: AudioRepository
    ): AudioUseCase() {
        suspend operator fun invoke(audio: Audio) = repository.addAudio(audio)
    }

    class UpdateAudio(
        private val repository: AudioRepository
    ): AudioUseCase() {
        suspend operator fun invoke(audio: Audio) = repository.updateAudio(audio)
    }

    class DeleteAudio(
        private val repository: AudioRepository
    ): AudioUseCase() {
        suspend operator fun invoke(audio: Audio) = repository.deleteAudio(audio)
    }
}