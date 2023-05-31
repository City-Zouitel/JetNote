package com.example.domain.usecase

import com.example.domain.model.Task
import com.example.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class TaskUseCase {

    class GetAllTaskItems @Inject constructor(
        private val repository: TaskRepository
    ): TaskUseCase() {
        operator fun invoke() = repository.getAllTaskItems
    }

    class AddTaskItem @Inject constructor(
        private val repository: TaskRepository
    ): TaskUseCase() {
        suspend operator fun invoke(task: Task) = repository.addTaskItem(task)
    }

    class UpdateTaskItem @Inject constructor(
        private val repository: TaskRepository
    ): TaskUseCase() {
        suspend operator fun invoke(task: Task) = repository.updateTaskItem(task)
    }

    class DeleteTaskItem @Inject constructor(
        private val repository: TaskRepository
    ): TaskUseCase() {
        suspend operator fun invoke(task: Task) = repository.deleteTaskItem(task)
    }
}
