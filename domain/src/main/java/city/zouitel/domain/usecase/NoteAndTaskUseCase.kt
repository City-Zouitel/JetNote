package city.zouitel.domain.usecase

import city.zouitel.domain.model.NoteAndTask
import city.zouitel.domain.repository.NoteAndTaskRepository

//@Singleton
sealed class NoteAndTaskUseCase {

    class GetAllNotesAndTask(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        operator fun invoke() = repository.getAllNotesAndTask
    }

    class AddNoteAndTask(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        suspend operator fun invoke(noteAndTask: NoteAndTask) = repository.addNoteAndTask(noteAndTask)
    }

    class DeleteNoteAndTask(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        suspend operator fun invoke(noteAndTask: NoteAndTask) = repository.deleteNoteAndTask(noteAndTask)
    }
}
