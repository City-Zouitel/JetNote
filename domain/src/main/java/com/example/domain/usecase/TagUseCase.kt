package com.example.domain.usecase

import com.example.domain.model.Tag
import com.example.domain.repository.TagRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class TagUseCase {

    class GetAllLabels @Inject constructor(
        private val repository: TagRepository
    ): TagUseCase() {
        operator fun invoke() = repository.getAllLabels
    }

    class AddTag @Inject constructor(
        private val repository: TagRepository
    ): TagUseCase() {
        suspend operator fun invoke(tag: Tag) = repository.addTag(tag)
    }

    class UpdateTag @Inject constructor(
        private val repository: TagRepository
    ): TagUseCase() {
        suspend operator fun invoke(tag: Tag) = repository.updateTag(tag)
    }

    class DeleteTag @Inject constructor(
        private val repository: TagRepository
    ): TagUseCase() {
        suspend operator fun invoke(tag: Tag) = repository.deleteTag(tag)
    }
}