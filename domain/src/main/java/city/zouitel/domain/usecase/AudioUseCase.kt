package city.zouitel.domain.usecase

import city.zouitel.domain.model.Audio
import city.zouitel.domain.repository.AudioRepo

sealed class AudioUseCase {
    class GetAllAudios(
        private val repository: AudioRepo
    ): AudioUseCase() {
        operator fun invoke() = repository.getAllAudios
    }

    class AddAudio(
        private val repository: AudioRepo
    ): AudioUseCase() {
        suspend operator fun invoke(audio: Audio) = repository.addAudio(audio)
    }

    class UpdateAudio(
        private val repository: AudioRepo
    ): AudioUseCase() {
        suspend operator fun invoke(audio: Audio) = repository.updateAudio(audio)
    }

    class DeleteAudio(
        private val repository: AudioRepo
    ): AudioUseCase() {
        suspend operator fun invoke(audio: Audio) = repository.deleteAudio(audio)
    }
}