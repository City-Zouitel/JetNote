package com.example.domain.usecase

import com.example.domain.repository.NoteRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class NoteUseCase {

    class GetAllNotesById @Inject constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesById
    }

    class GetAllNotesByName @Inject constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByName
    }

    class GetAllNotesByNewest @Inject constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByNewest
    }

    class GetAllNotesByOldest @Inject constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllNotesByOldest
    }

    class GetAllTrashedNotes @Inject constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllTrashedNotes
    }

    class AllNotesByPriority @Inject constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.allNotesByPriority
    }

    class GetAllRemindingNotes @Inject constructor(
        private val repository: NoteRepository
    ): NoteUseCase() {
        operator fun invoke() = repository.getAllRemindingNotes
    }
}
