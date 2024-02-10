package city.zouitel.tags.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import city.zouitel.tags.viewmodel.NoteAndTagViewModel
import city.zouitel.tags.viewmodel.TagViewModel

internal sealed class State {

    @Stable
    class Tag(
        private val viewModel: TagViewModel
    ) : State() {
        val rememberAllTags
            get() = @Composable {
                remember(
                    viewModel,
                    viewModel::getAllLTags
                ).collectAsState().value
            }
    }

    @Stable
    class NoteTag(
        private val viewModel: NoteAndTagViewModel
    ) : State() {

        val rememberAllNoteTags
            @Composable get() =
                remember(
                    viewModel,
                    viewModel::getAllNotesAndTags
                ).collectAsState().value
    }
}
