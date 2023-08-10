package com.example.tags.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tags.viewmodel.NoteAndTagViewModel
import com.example.tags.viewmodel.TagViewModel

internal sealed class State {

    @Stable
    class Tag(
        private val viewModel: TagViewModel
    ) : State() {
        val rememberAllTags
            @Composable get() =
                remember(
                    viewModel,
                    viewModel::getAllLTags
                ).collectAsStateWithLifecycle().value

    }

    @Stable
    class NoteTag(
        private val viewModel: NoteAndTagViewModel
    ): State() {

        val rememberAllNoteTags
            @Composable get() =
                remember(
                    viewModel,
                    viewModel::getAllNotesAndTags
                ).collectAsStateWithLifecycle().value
    }
}
