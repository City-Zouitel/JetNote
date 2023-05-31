package com.example.domain.usecase

import com.example.domain.repository.NoteAndTagRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class NoteAndTagUseCase {
    class GetAllNotesAndTags @Inject constructor(
        repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {

    }

    class AddNoteAndTag @Inject constructor(
        repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {

    }

    class DeleteNoteAndTag @Inject constructor(
        repository: NoteAndTagRepository
    ): NoteAndTagUseCase() {

    }

}
