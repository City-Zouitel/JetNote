package com.example.domain.usecase

import com.example.domain.repository.TagRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class TagUseCase {

    class GetAllLabels @Inject constructor(
        repository: TagRepository
    ): TagUseCase() {

    }

    class AddTag @Inject constructor(
        repository: TagRepository
    ): TagUseCase() {

    }

    class UpdateTag @Inject constructor(
        repository: TagRepository
    ): TagUseCase() {

    }

    class DeleteTag @Inject constructor(
        repository: TagRepository
    ): TagUseCase() {

    }
}