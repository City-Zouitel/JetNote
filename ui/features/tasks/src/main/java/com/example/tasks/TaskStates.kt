package com.example.tasks

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle

sealed class TaskStates {

    @Stable
    class Task(
        private val viewModel: TaskViewModel
    ) : TaskStates() {

        val rememberAllTasks
            @Composable get() = remember(
                viewModel,
                viewModel::getAllTaskList
            ).collectAsStateWithLifecycle().value
    }

    @Stable
    class NoteTask(
        private val viewModel: NoteAndTaskViewModel
    ) : TaskStates() {

        val rememberAllNoteTasks
            @Composable get() = remember(
                viewModel,
                viewModel::getAllNotesAndTask
            ).collectAsStateWithLifecycle().value
    }
}
