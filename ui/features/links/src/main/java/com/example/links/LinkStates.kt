package com.example.links

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.links.ui.LinkVM
import com.example.links.ui.NoteAndLinkVM

sealed class LinkStates {

    @Stable
    class Link(
        private val viewModel: LinkVM
    ) : LinkStates() {
        val rememberAllLinks
            @Composable get() = remember(
                viewModel,
                viewModel::getAllLinks
            ).collectAsStateWithLifecycle().value
    }

    @Stable
    class NoteLinks(
        private val viewModel: NoteAndLinkVM
    ) : LinkStates() {
        val rememberAllNoteLinks
            @Composable get() = remember(
                viewModel,
                viewModel::getAllNotesAndLinks
            ).collectAsStateWithLifecycle().value
    }
}
