package com.example.domain.usecase

import com.example.domain.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
sealed class TaskUseCase {

    class GetAllTaskItems @Inject constructor(
        repository: TaskRepository
    ): TaskUseCase() {

    }

    class AddTaskItem @Inject constructor(
        repository: TaskRepository
    ): TaskUseCase() {

    }

    class UpdateTaskItem @Inject constructor(
        repository: TaskRepository
    ): TaskUseCase() {

    }

    class DeleteTaskItem @Inject constructor(
        repository: TaskRepository
    ): TaskUseCase() {

    }

}
