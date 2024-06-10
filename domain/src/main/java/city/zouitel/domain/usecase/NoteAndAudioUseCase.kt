package city.zouitel.domain.usecase

import city.zouitel.domain.model.NoteAndAudio
import city.zouitel.domain.repository.NoteAndAudioRepository

sealed class NoteAndAudioUseCase {

    class GetAllNotesAndAudios(
        private val repository: NoteAndAudioRepository
    ): NoteAndLinkUseCase() {
        operator fun invoke() = repository.getAllNotesAndAudio
    }

    class AddNoteAndAudio(
        private val repository: NoteAndAudioRepository
    ): NoteAndLinkUseCase() {
        suspend operator fun invoke(noteAndAudio: NoteAndAudio) = repository.addNoteAndAudio(noteAndAudio)
    }

    class UpdateNoteAndAudio(
        private val repository: NoteAndAudioRepository
    ): NoteAndLinkUseCase() {
        suspend operator fun invoke(noteAndAudio: NoteAndAudio) = repository.updateNoteAndAudio(noteAndAudio)
    }

    class DeleteNoteAndAudio(
        private val repository: NoteAndAudioRepository
    ): NoteAndLinkUseCase() {
        suspend operator fun invoke(noteAndAudio: NoteAndAudio) = repository.deleteNoteAndAudio(noteAndAudio)
    }
}