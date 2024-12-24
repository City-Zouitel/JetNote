package city.zouitel.domain.usecase

import city.zouitel.domain.model.Task
import city.zouitel.domain.repository.TaskRepository

sealed class TaskUseCase {

    data class ObserveAll(private val repo: TaskRepository): TaskUseCase() {
        operator fun invoke() = repo.observeAll
    }

    data class ObserveByUid(private val repo: TaskRepository): TaskUseCase() {
        operator fun invoke(uid: String) = repo.observeByUid(uid)
    }

    data class Insert(private val repo: TaskRepository): TaskUseCase() {
        suspend operator fun invoke(task: Task) = repo.insert(task)
    }

    data class UpdateById(private val repo: TaskRepository): TaskUseCase() {
        suspend operator fun invoke(id: Long) = repo.updateById(id)
    }

    data class DeleteById(private val repo: TaskRepository): TaskUseCase() {
        suspend operator fun invoke(id: Long) = repo.deleteById(id)
    }
}
