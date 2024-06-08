package city.zouitel.domain.usecase

import city.zouitel.domain.model.Task
import city.zouitel.domain.repository.TaskRepository

//@Singleton
sealed class TaskUseCase {

    class GetAllTaskItems(
        private val repository: TaskRepository
    ): TaskUseCase() {
        operator fun invoke() = repository.getAllTaskItems
    }

    class AddTaskItem(
        private val repository: TaskRepository
    ): TaskUseCase() {
        suspend operator fun invoke(task: Task) = repository.addTaskItem(task)
    }

    class UpdateTaskItem(
        private val repository: TaskRepository
    ): TaskUseCase() {
        suspend operator fun invoke(task: Task) = repository.updateTaskItem(task)
    }

    class DeleteTaskItem(
        private val repository: TaskRepository
    ): TaskUseCase() {
        suspend operator fun invoke(task: Task) = repository.deleteTaskItem(task)
    }
}
