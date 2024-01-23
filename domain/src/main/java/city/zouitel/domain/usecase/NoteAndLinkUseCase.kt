package city.zouitel.domain.usecase

import city.zouitel.domain.model.Link
import city.zouitel.domain.model.NoteAndLink
import city.zouitel.domain.repository.NoteAndLinkRepository

//@Singleton
sealed class NoteAndLinkUseCase {
    class GetAllNotesAndLinks /*@Inject*/ constructor(
        private val repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {
        operator fun invoke() = repository.getAllNotesAndLinks
    }

    class AddNoteAndLink /*@Inject*/ constructor(
        private val repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {
        suspend operator fun invoke(noteAndLink: NoteAndLink) = repository.addNoteAndLink(noteAndLink)
    }

    class DeleteNoteAndLink /*@Inject*/ constructor(
        private val repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {
        suspend operator fun invoke(noteAndLink: NoteAndLink) = repository.deleteNoteAndLink(noteAndLink)
    }
}
