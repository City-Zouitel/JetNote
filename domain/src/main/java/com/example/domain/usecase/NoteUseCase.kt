package com.example.domain.usecase

import com.example.domain.repository.NoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class NoteUseCase {

    class GetAllNotesById @Inject constructor(
        repository: NoteRepository
    ): NoteUseCase() {

    }

    class GetAllNotesByName @Inject constructor(
        repository: NoteRepository
    ): NoteUseCase() {

    }

    class GetAllNotesByNewest @Inject constructor(
        repository: NoteRepository
    ): NoteUseCase() {

    }

    class GetAllNotesByOldest @Inject constructor(
        repository: NoteRepository
    ): NoteUseCase() {

    }

    class GetAllTrashedNotes @Inject constructor(
        repository: NoteRepository
    ): NoteUseCase() {

    }

    class AllNotesByPriority @Inject constructor(
        repository: NoteRepository
    ): NoteUseCase() {

    }

    class GetAllRemindingNotes @Inject constructor(
        repository: NoteRepository
    ): NoteUseCase() {

    }
}
