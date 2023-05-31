package com.example.domain.usecase

import com.example.domain.model.NoteAndTag
import com.example.domain.repository.NoteAndTagRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class NoteAndTagUseCase {
    class GetAllNotesAndTags @Inject constructor(
        private val repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {
        operator fun invoke() = repository.getAllNotesAndTags
    }

    class AddNoteAndTag @Inject constructor(
        private val repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {
        suspend operator fun invoke(noteAndTag: NoteAndTag) = repository.addNoteAndTag(noteAndTag)
    }

    class DeleteNoteAndTag @Inject constructor(
        private val repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {
        suspend operator fun invoke(noteAndTag: NoteAndTag) = repository.deleteNoteAndTag(noteAndTag)
    }
}
