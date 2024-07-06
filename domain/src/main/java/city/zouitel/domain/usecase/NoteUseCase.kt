package city.zouitel.domain.usecase

import city.zouitel.domain.repository.NoteRepository

//@Singleton
sealed class NoteUseCase {

    class GetAllNotesById(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesById
    }

    class GetAllNotesByName(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByName
    }

    class GetAllNotesByNewest(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByNewest
    }

    class GetAllNotesByOldest(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByOldest
    }

    class GetAllRemovedNotes(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllRemovedNotes
    }

    class GetAllNotesByPriority(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.allNotesByPriority
    }

    class GetAllRemindingNotes(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllRemindingNotes
    }
}
