package city.zouitel.domain.usecase

import city.zouitel.domain.model.NoteAndTag
import city.zouitel.domain.repository.NoteAndTagRepository

//@Singleton
sealed class NoteAndTagUseCase {
    class GetAllNotesAndTags(
        private val repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {
        operator fun invoke() = repository.getAllNotesAndTags
    }

    class AddNoteAndTag(
        private val repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {
        suspend operator fun invoke(noteAndTag: NoteAndTag) = repository.addNoteAndTag(noteAndTag)
    }

    class DeleteNoteAndTag(
        private val repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {
        suspend operator fun invoke(noteAndTag: NoteAndTag) = repository.deleteNoteAndTag(noteAndTag)
    }
}
