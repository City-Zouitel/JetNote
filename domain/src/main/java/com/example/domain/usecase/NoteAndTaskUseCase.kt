package com.example.domain.usecase

import com.example.domain.repository.NoteAndTaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class NoteAndTaskUseCase {

    class GetAllNotesAndTask @Inject constructor(
        repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {

    }

    class AddNoteAndTask @Inject constructor(
        repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {

    }

    class DeleteNoteAndTask @Inject constructor(
        repository: NoteAndTaskRepository
    ): NoteAndTaskUseCase() {

    }

}
