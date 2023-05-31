package com.example.domain.usecase

import com.example.domain.repository.NoteAndLinkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class NoteAndLinkUseCase {
    class GetAllNotesAndLinks @Inject constructor(
        repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {

    }

    class AddNoteAndLink @Inject constructor(
        repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {

    }

    class DeleteNoteAndLink @Inject constructor(
        repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {

    }
}
