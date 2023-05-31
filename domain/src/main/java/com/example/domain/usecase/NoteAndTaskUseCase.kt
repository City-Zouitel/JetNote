package com.example.domain.usecase

import com.example.domain.model.NoteAndTask
import com.example.domain.repository.NoteAndTaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class NoteAndTaskUseCase {

    class GetAllNotesAndTask @Inject constructor(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        operator fun invoke() = repository.getAllNotesAndTask
    }

    class AddNoteAndTask @Inject constructor(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        suspend operator fun invoke(noteAndTask: NoteAndTask) = repository.addNoteAndTask(noteAndTask)
    }

    class DeleteNoteAndTask @Inject constructor(
        private val repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {
        suspend operator fun invoke(noteAndTask: NoteAndTask) = repository.deleteNoteAndTask(noteAndTask)
    }
}
