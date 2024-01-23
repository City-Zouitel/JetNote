package city.zouitel.domain.usecase

import city.zouitel.domain.model.NoteAndTask
import city.zouitel.domain.repository.NoteAndTaskRepository

//@Singleton
sealed class NoteAndTaskUseCase {

    class GetAllNotesAndTask /*@Inject*/ constructor(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        operator fun invoke() = repository.getAllNotesAndTask
    }

    class AddNoteAndTask /*@Inject*/ constructor(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        suspend operator fun invoke(noteAndTask: NoteAndTask) = repository.addNoteAndTask(noteAndTask)
    }

    class DeleteNoteAndTask /*@Inject*/ constructor(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        suspend operator fun invoke(noteAndTask: NoteAndTask) = repository.deleteNoteAndTask(noteAndTask)
    }
}
