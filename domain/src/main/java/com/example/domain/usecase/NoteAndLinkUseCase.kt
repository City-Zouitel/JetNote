package com.example.domain.usecase

import com.example.domain.model.Link
import com.example.domain.model.NoteAndLink
import com.example.domain.repository.NoteAndLinkRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class NoteAndLinkUseCase {
    class GetAllNotesAndLinks @Inject constructor(
        private val repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {
        operator fun invoke() = repository.getAllNotesAndLinks
    }

    class AddNoteAndLink @Inject constructor(
        private val repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {
        suspend operator fun invoke(noteAndLink: NoteAndLink) = repository.addNoteAndLink(noteAndLink)
    }

    class DeleteNoteAndLink @Inject constructor(
        private val repository: NoteAndLinkRepository
    ): NoteAndLinkUseCase() {
        suspend operator fun invoke(noteAndLink: NoteAndLink) = repository.deleteNoteAndLink(noteAndLink)
    }
}
