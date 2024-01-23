package city.zouitel.domain.usecase

import city.zouitel.domain.repository.NoteRepository

//@Singleton
sealed class NoteUseCase {

    class GetAllNotesById /*@Inject*/ constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesById
    }

    class GetAllNotesByName /*@Inject*/ constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByName
    }

    class GetAllNotesByNewest /*@Inject*/ constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByNewest
    }

    class GetAllNotesByOldest /*@Inject*/ constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByOldest
    }

    class GetAllTrashedNotes /*@Inject*/ constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllTrashedNotes
    }

    class GetAllNotesByPriority /*@Inject*/ constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.allNotesByPriority
    }

    class GetAllRemindingNotes /*@Inject*/ constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllRemindingNotes
    }
}
