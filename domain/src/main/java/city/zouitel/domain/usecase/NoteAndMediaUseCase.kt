package city.zouitel.domain.usecase

import city.zouitel.domain.model.NoteAndMedia
import city.zouitel.domain.repository.NoteAndMediaRepository

sealed class NoteAndMediaUseCase {

    class GetAllNotesAndMedia(private val repo: NoteAndMediaRepository): NoteAndAudioUseCase() {
        operator fun invoke() = repo.getAllNotesAndMedia
    }

    class AddNoteAndMedia(private val repo: NoteAndMediaRepository): NoteAndAudioUseCase() {
        operator fun invoke(noteAndMedia: NoteAndMedia) = repo.addNoteAndMedia(noteAndMedia)
    }

    class UpdateNoteAndMedia(private val repo: NoteAndMediaRepository): NoteAndAudioUseCase() {
        operator fun invoke(noteAndMedia: NoteAndMedia) = repo.updateNoteAndMedia(noteAndMedia)
    }

    class DeleteNoteAndMedia(private val repo: NoteAndMediaRepository): NoteAndAudioUseCase() {
        operator fun invoke(noteAndMedia: NoteAndMedia) = repo.deleteNoteAndMedia(noteAndMedia)
    }
}