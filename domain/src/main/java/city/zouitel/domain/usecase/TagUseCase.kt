package city.zouitel.domain.usecase

import city.zouitel.domain.model.Tag
import city.zouitel.domain.repository.TagRepository

//@Singleton
sealed class TagUseCase {

    class GetAllTags /*@Inject*/ constructor(
        private val repository: TagRepository
    ): TagUseCase() {
        operator fun invoke() = repository.getAllTags
    }

    class AddTag /*@Inject*/ constructor(
        private val repository: TagRepository
    ): TagUseCase() {
        suspend operator fun invoke(tag: Tag) = repository.addTag(tag)
    }

    class UpdateTag /*@Inject*/ constructor(
        private val repository: TagRepository
    ): TagUseCase() {
        suspend operator fun invoke(tag: Tag) = repository.updateTag(tag)
    }

    class DeleteTag /*@Inject*/ constructor(
        private val repository: TagRepository
    ): TagUseCase() {
        suspend operator fun invoke(tag: Tag) = repository.deleteTag(tag)
    }
}