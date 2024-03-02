package city.zouitel.tags.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import city.zouitel.tags.viewmodel.NoteAndTagScreenModel
import city.zouitel.tags.viewmodel.TagScreenModel

internal sealed class State {

    @Stable
    class Tag(
        private val viewModel: TagScreenModel
    ) : State() {
        val rememberAllTags
        @Composable get() =
                remember(
                    viewModel,
                    viewModel::getAllLTags
                ).collectAsState().value
    }

    @Stable
    class NoteTag(
        private val screenModel: NoteAndTagScreenModel
    ) : State() {

        val rememberAllNoteTags
            @Composable get() =
                remember(
                    screenModel,
                    screenModel::getAllNotesAndTags
                ).collectAsState().value
    }
}
