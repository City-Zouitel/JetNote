package com.example.tags.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tags.viewmodel.NoteAndTagViewModel
import com.example.tags.viewmodel.TagViewModel

sealed class TagStates {

    @Stable
    class Tag(
        private val viewModel: TagViewModel
    ) : TagStates() {
        val rememberAllTags
            @Composable get() = remember(
                viewModel,
                viewModel::getAllLTags
            ).collectAsStateWithLifecycle().value
    }

    @Stable
    class NoteTag(
        private val viewModel: NoteAndTagViewModel
    ): TagStates() {

        val rememberAllNoteTags
            @Composable get() =
                remember(
                    viewModel,
                    viewModel::getAllNotesAndTags
                ).collectAsStateWithLifecycle().value
    }
}
